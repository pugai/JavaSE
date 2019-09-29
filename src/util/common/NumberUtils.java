package util.common;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @Description
 * @Auther ctl
 * @Date 2019/7/18
 */
public class NumberUtils {

    public static String formatPercent(long numerator, long denominator, int precision) {
        StringBuilder pattern = new StringBuilder("0");
        if (precision > 0) {
            pattern.append(".");
            for (; precision > 0; pattern.append("0"), precision--);
        }
        NumberFormat nf = new DecimalFormat(pattern.toString());
        nf.setRoundingMode(RoundingMode.HALF_UP);
        return nf.format((double) numerator / (double) denominator * 100) + "%";
    }

}
