package test;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;
import util.common.NumberUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
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

    @Test
    public void testParallelStream() {
        List<Integer> a = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            a.add(i);
        }
        System.out.println(a);
        List<Integer> b = a.stream().filter(i -> i % 2 == 1).collect(Collectors.toList());
        System.out.println(b);
        // parallelStream 也会保持原有顺序
        List<Integer> c = a.parallelStream().filter(i -> i % 2 == 1).collect(Collectors.toList());
        System.out.println(c);
        // 测试方法引用
        TestFunctionInterface t = String::codePointCount;
    }


    private static final Pattern p0 = Pattern.compile("[\\n|\\r|\\t|\\s]");
    private static final Pattern p1 = Pattern.compile("</p><p>");
    private static final Pattern p2 = Pattern.compile("<[/]?p>");
    private static final Pattern p3 = Pattern.compile("&nbsp;");

    @Test
    public void testString() {
        StringBuilder sb = new StringBuilder();
        sb.append("七见倾心：毒舌总裁娶佳妻").append("\r\n\r\n");

        sb.append("01魔鬼娶妻").append("\r\n\r\n");
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("C:\\Users\\ctl\\Desktop\\7916604666267757175")), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content = p0.matcher(content).replaceAll("");
        content = p1.matcher(content).replaceAll("\r\n");
        content = p2.matcher(content).replaceAll("");
        content = p3.matcher(content).replaceAll(" ");
        sb.append(content).append("\r\n\r\n");

        sb.append("02 “光荣”的任务").append("\r\n\r\n");
        content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("C:\\Users\\ctl\\Desktop\\7917011485570257741")), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content = p0.matcher(content).replaceAll("");
        content = p1.matcher(content).replaceAll("\r\n");
        content = p2.matcher(content).replaceAll("");
        content = p3.matcher(content).replaceAll(" ");
        sb.append(content).append("\r\n\r\n");

        try {
            Files.write(Paths.get("C:\\Users\\ctl\\Desktop\\书籍.txt"), sb.toString().getBytes(Charset.forName("UTF-8")), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
