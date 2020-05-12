package test;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;
import util.common.NumberUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
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
import java.util.stream.Stream;

/**
 * @Description
 * @Auther ctl
 * @Date 2019/5/28
 */
public class SimpleTest {

    @Test
    public void testParallCollect() {
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
//        Set<Integer> collect = Lists.partition(list, 1000).parallelStream().flatMap(Collection::stream).collect(Collectors.toSet());
//        System.out.println(collect.size());

        List<Integer> result = list.parallelStream().filter(i -> i % 2 == 1).skip(100).limit(20).collect(Collectors.toList());
//        System.out.println(result.size());
//        result.forEach(System.out::println);




        System.out.println(result);
    }

    @Test
    public void testLinkHashSet() {
        Set<String> set = Sets.newLinkedHashSet();
        set.add("b11");
        set.add("b11");
        set.add("b11");
        set.add("a22");
        set.add("a11");
        set.add("bbb");
        set.add("bbb");
        set.add("bbb");
        set.add("bbb");
        set.add("bbb");
        set.add("dddd");
        set.add("sjfslkfjsk");
        List<String> list = Lists.newArrayList();
        int i = 1;
        for (String s : set) {
            list.add(s + "-" + i++);
        }
        System.out.println(list);
    }

    @Test
    public void testFindAny() {
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7");
        for (int i = 0; i < 10; i++) {
//            System.out.println(list.stream().findAny().get());
//            Collections.shuffle(list);
//            System.out.println(list.get(0));
            System.out.println(list.get(new Random().nextInt(list.size())));
        }
    }

    @Test
    public void test11() throws IOException {
        new File("C:\\Users\\ctl\\Desktop\\test.txt").createNewFile();
    }

    private static int num = 0;

    @Test
    public void checkUserId() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\ctl\\Desktop\\云阅读暗扣\\junkuserid.txt")));
        String line;
        int count = 0;
        List<Long> userIds = new ArrayList<>(1000);
        while ((line = reader.readLine()) != null) {
            userIds.add(Long.valueOf(line));
            count++;
            if (userIds.size() == 1000) {
                importUser(userIds);
                userIds.clear();
            }
        }
        if (userIds.size() > 0) {
            importUser(userIds);
        }
        reader.close();
        System.out.println(count);
        System.out.println(num);
    }

    private void importUser(List<Long> userIds) {
        num++;
        System.out.println(userIds.size());
    }

    @Test
    public void test1() {
        System.out.println(new BigDecimal("222.22").multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN).intValueExact());
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

    @Test
    public void testParallelStreamToSort() {
        List<String> list = new ArrayList<>();
        list.add("C");
        list.add("H");
        list.add("A");
        list.add("A");
        list.add("B");
        list.add("F");
        list.add("");

//        list.parallelStream() // in parallel, not just concurrently!
//                .filter(s -> !s.isEmpty()) // remove empty strings
//                .distinct() // remove duplicates
//                .sorted() // sort them
//                .forEach(System.out::println); // print each item
//                .forEachOrdered(System.out::println);

        list = list.parallelStream() // in parallel, not just concurrently!
                .filter(s -> !s.isEmpty()) // remove empty strings
                .distinct() // remove duplicates
                .sorted() // sort them
                .collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void testSplitter() {
        String s = "a\n  \nb\n\nc\n";
        System.out.println(Splitter.on("\n").trimResults().omitEmptyStrings().split(s));
        System.out.println(Splitter.on("\n").omitEmptyStrings().trimResults().split(s));
    }

    @Test
    public void testUnion() {
//        HashSet<String> s1 = Sets.newHashSet("a", "b", "c");
//        HashSet<String> s2 = Sets.newHashSet("c", "d");
//        Sets.SetView<String> union = Sets.union(s1, s2);
//        System.out.println(union);
//        System.out.println(Sets.difference(s1, s2));
//        System.out.println(Sets.difference(s2, s1));
//        Integer i = null;
//        int ii = 0;
//        System.out.println(Objects.equals(i, ii));

        String s = "\u2B50签到送币aaaa";
        System.out.println(NOT_LETTER_NUMBER_CHIN.matcher(s).replaceAll(""));
        System.out.println(s.length());
        System.out.println(s.charAt(0));
        System.out.println(s.substring(0, 1));
        System.out.println(s.replace("\u2B50", ""));
        System.out.println(s.replaceAll("[^\u4E00-\u9FBF]", "x"));
        System.out.println("\u0030\u0039\u0041\u005A\u0061\u007A\u25A0");
    }

    @Test
    public void testSystem() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    public static final Pattern NOT_LETTER_NUMBER_CHIN = Pattern.compile("[^\u4E00-\u9FBF\u0030-\u0039\u0041-\u005A\u0061-\u007A]");


    private static final Pattern p0 = Pattern.compile("[\\n|\\r|\\t|\\s]");
//    private static final Pattern p1 = Pattern.compile("</p><p>");
//    private static final Pattern p2 = Pattern.compile("<[/]?p>");
    private static final Pattern p1 = Pattern.compile("</[^>]*><[a-zA-Z!\\?][^>]*>");
    private static final Pattern p2 = Pattern.compile("<[a-zA-Z!\\/\\?][^>]*>");
    private static final Pattern p3 = Pattern.compile("&nbsp;");

    @Test
    public void testString() {
        StringBuilder sb = new StringBuilder();
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("C:\\Users\\ctl\\Desktop\\bj1")), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content = p0.matcher(content).replaceAll("");
        content = p1.matcher(content).replaceAll("\r\n");
        content = p2.matcher(content).replaceAll("");
        content = p3.matcher(content).replaceAll(" ");
        sb.append(content).append("\r\n\r\n");

        content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("C:\\Users\\ctl\\Desktop\\bj2")), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        content = p0.matcher(content).replaceAll("");
        content = p1.matcher(content).replaceAll("\r\n");
        content = p2.matcher(content).replaceAll("");
        content = p3.matcher(content).replaceAll(" ");
        sb.append(content).append("\r\n\r\n");

        try {
            Files.write(Paths.get("C:\\Users\\ctl\\Desktop\\result.txt"), sb.toString().getBytes(Charset.forName("UTF-8")), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegex() {
        String result = "ab|cd";
        System.out.println(result);
        result = result.replaceAll("\\|", "\\\\|");
        System.out.println(result);
    }

    @Test
    public void testListSub() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println(list.stream().skip(5).collect(Collectors.toList()));
        System.out.println(list.subList(0,2));
        System.out.println(list);
    }

    @Test
    public void testGroupBy() {
        List<Person> ps = new ArrayList<>();
        ps.add(new Person(1L, "tt", 12));
        ps.add(new Person(2L, "ee", 13));
        ps.add(new Person(3L, "aa", 13));
        System.out.println(ps.stream().collect(Collectors.groupingBy(Person::getAge)));
    }

    @Test
    public void testSet() {
        Set<String> s1 = new HashSet<>(Arrays.asList("a", "b", "c", "d"));
        Set<String> s2 = new HashSet<>(Arrays.asList("a", "b"));
        s1.removeAll(s2);
        System.out.println(s1);
        System.out.println(s2);
    }

    class Person {
        private Long id;
        private String name;
        private Integer age;

        public Person(Long id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
