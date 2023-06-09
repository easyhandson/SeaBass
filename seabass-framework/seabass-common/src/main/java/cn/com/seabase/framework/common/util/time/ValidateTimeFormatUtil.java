package cn.com.seabase.framework.common.util.time;

import java.util.regex.Pattern;

/**
 * 校验日期时间格式工具类
 *
 * 正则传送门: https://blog.csdn.net/chuixue24/article/details/111352791
 * 注: 后续有需可自行添加, 按照规范来即可!!!
 */
public final class ValidateTimeFormatUtil {

    /**
     * yyyy-MM-dd HH:mm:ss 格式(可匹配校验闰年, 日期和时间中间可存在多个空格)   虽说命名不规范... 但是好在通俗易懂
     */
    private static final Pattern yyyy_MM_dd_HH_mm_ss = Pattern.compile("^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))" +
            "\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$");

    /**
     * yyyy-MM-dd HH:mm
     */
    private static final Pattern yyyy_MM_dd_HH_mm = Pattern.compile("^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))" +
            "\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9])$");

    /**
     * yyyy/MM/dd HH:mm
     */
    private static final Pattern yyyyLMMLdd_HH_mm = Pattern.compile("^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))/02/29))" +
            "\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9])$");


    /**
     * 校验日期时间格式是否为 yyyy-MM-dd HH:mm:ss 格式
     * @param date 日期时间参数
     * @return true-符合  false-不符合
     */
    public static boolean yyyy_MM_dd_HH_mm_ss(String date) {
        return yyyy_MM_dd_HH_mm_ss.matcher(date).matches();
    }

    /**
     * 校验日期时间格式是否为 yyyy-MM-dd HH:mm 格式
     * @param date 日期时间参数
     * @return true-符合  false-不符合
     */
    public static boolean yyyy_MM_dd_HH_mm(String date) {
        return yyyy_MM_dd_HH_mm.matcher(date).matches();
    }

    /**
     * 校验日期时间格式是否为 yyyy/MM/dd HH:mm 格式
     * @param date 日期时间参数
     * @return true-符合  false-不符合
     */
    public static boolean yyyyLMMLdd_HH_mm(String date) {
        return yyyyLMMLdd_HH_mm.matcher(date).matches();
    }

}
