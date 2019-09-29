package jvm;

public class Main {
    public static void main(String[] args) {
        // 下面语句中，aaa也已经放入常量池，hotspot默默地创建aaa字符串，并且调用intern方法！
        String s1 = new StringBuilder("aaa").append("bbbb").toString();
        System.out.println(s1.intern() == s1);
        // String s2 = new StringBuilder("ja").append("va").toString();
        // System.out.println(s2.intern() == s2);
        String s3 = new StringBuilder("ja").toString();
        System.out.println(s3.intern() == s3);
        String s4 = new String("ttt");
        System.out.println(s4.intern() == s4);
        String s5 = "qqq";
        System.out.println(s5.intern() == s5);
        String s6 = new StringBuilder("a").append("aa").toString();
        System.out.println(s6.intern() == s6);
    }
}