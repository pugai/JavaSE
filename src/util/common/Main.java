package util.common;

/**
 * @Description
 * @Auther ctl
 * @Date 2019/5/27
 */
public class Main {


    public static void splite() {
        String s = "";
        String result = null;
        if (org.apache.commons.lang3.StringUtils.isBlank(s)) {
            result = "[]";
        }
        String s1 = s.substring(1, s.length() - 1);
        if (s1.length() > 0) {
            String[] arr = s1.split(",");
            for (String ss: arr) {
                if (!ss.equals("\"\"")) {
                    result = "[" + ss + "]";
                    break;
                }
            }
        }
        if (result == null) {
            result = "[]";
        }
        System.out.println(result);





//		System.out.println(s.substring(1, s.length() - 1).split(",")[0]);
    }



}
