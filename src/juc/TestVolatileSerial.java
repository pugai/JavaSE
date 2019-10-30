package juc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description volatile 有序性测试
 * @Auther ctl
 * @Date 2019/10/30
 */
public class TestVolatileSerial {

    private static int x, y;

    public static void main(String[] args) throws InterruptedException {
        Set<String> result = new HashSet<>();
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 1000000; i++) {
            x = 0;
            y = 0;
            Thread one = new Thread(() -> {
                int a = y;
                x = 1;
                map.put("a", a);
            });
            Thread two = new Thread(() -> {
                int b = x;
                y = 1;
                map.put("b", b);
            });
            one.start();
            two.start();
            one.join();
            two.join();
            result.add("a=" + map.get("a") + "," + "b=" + map.get("b"));
            // 结果中会出现 a=1,b=1，指令重排序问题
            System.out.println(result);
        }
    }

}
