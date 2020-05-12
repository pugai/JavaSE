package regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ctl
 * @date 2019/12/2
 */
public class RegexTest {
    @Test
    public void testRegex() {
        String s = "xxaa11bbyy";
        Pattern p = Pattern.compile("aa(.*)bb");
        Matcher matcher = p.matcher(s);
        System.out.println(matcher.replaceAll("$1end"));
//        while (matcher.find()) {
//            System.out.println(matcher.group());
//            System.out.println(matcher.group(1));
//            System.out.println(matcher.group(2));
//            System.out.println(matcher.group(3));
//        }
    }
}
