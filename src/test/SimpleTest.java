package test;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;
import util.common.NumberUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Auther ctl
 * @Date 2019/5/28
 */
public class SimpleTest {

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList("a", "b", "c", "d", "e", "f"));
        list = list.stream().limit(3).collect(Collectors.toList());
        System.out.println(list);

        if (list == null) {
        }

        if (true) {
            System.out.println();
        }

    }

    @Test
    public void test2() {
        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        map.put("c", "d");
        System.out.println(Joiner.on("\n").withKeyValueSeparator("=").join(map));
    }

    @Test
    public void test3() throws NoSuchFieldException, IllegalAccessException {
        Map<String, String> m = new HashMap<>(1);
        m.put("k", "v");
        Class<? extends Map> a = m.getClass();
        System.out.println(a);
        Field threshold = a.getDeclaredField("threshold");
        threshold.setAccessible(true);
        Object o = threshold.get(m);
        System.out.println(o);
    }

    @Test
    public void test4() {
        Map<String, Map<String, String>> m = new HashMap<>();
        m.put("a", new HashMap<String, String>() {
            {
                put("test1", "value1");
            }
        });
//        m.put("b", new HashMap<String, String>() {
//            {
//                put("btest1", "bvalue1");
//            }
//        });
        m.computeIfAbsent("b", k -> new HashMap<>()).put("test2", "value2");


        System.out.println(m);
    }


    @Test
    public void testNumberFormat() {
        int p = 4;
        StringBuilder pattern = new StringBuilder("0");
        if (p > 0) {
            pattern.append(".");
            for (; p > 0; pattern.append("0"), p--);
        }
//        pattern.append("%");
        NumberFormat nf = new DecimalFormat(pattern.toString());
        nf.setRoundingMode(RoundingMode.HALF_UP);
//        System.out.println(nf.format(0.98545 * 100));

        System.out.println(NumberUtils.formatPercent(12, 123, 0));

    }

    @Test
    public void testRandomString() {
        System.out.println(RandomStringUtils.random(8, true, false));
        System.out.println(RandomStringUtils.random(8, true, true));
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }

    @Test
    public void testMutableInt() {
        Integer i = 3100;
        MutableInt m = new MutableInt();
        m.add(i);
        System.out.println(m.toInteger());
    }

    @Test
    public void testReadLine() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ctl\\Desktop\\test.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("[" + line + "]");
        }
    }

}
