package cn.com.seabase.framework.common.util.time;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 时间操作helper类 (对于数据库存的是10位数据时间戳使用)
 *
 * 注: 主要用来操作时间戳, LocalDateTime, 日期字符串之间的相互转换  (hutool里面好像没有, 所以自己写了一个)
 */
@Slf4j
public class DateTimeHelper {

    public static final String yyyy_MM_dd_HH_mm_ss_Str = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat SDF = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss_Str);
    
    private static final DateTimeFormatter yyyy_MM_dd_HH_mm_ss = DateTimeFormatter.ofPattern(yyyy_MM_dd_HH_mm_ss_Str);


    /**
     * 十位时间戳 转 LocalDateTime
     * */
    public static LocalDateTime timestampToDate(long timestamp){
        return LocalDateTime.ofEpochSecond(timestamp,0, ZoneOffset.ofHours(8));
    }

    /**
     * 十位时间戳 转 yyyy-MM-dd HH:mm:ss 时间格式字符串
     * */
    public static String timestampToDateStr(long timestamp){
        LocalDateTime localDateTime = timestampToDate(timestamp);
        return localDateTime.format(yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * yyyy-MM-dd HH:mm:ss 时间格式字符串 转 十位时间戳
     * 注: 传入 dateTime 为 null 或空字符串返回 null
     */
    public static Integer dateStrToTimestamp(String dateTime){
        if (StrUtil.isBlank(dateTime)) {
            return null;
        }
        long timestamp;
        try {
            timestamp = SDF.parse(dateTime).getTime() / 1000;
        } catch (ParseException e) {
            log.error("[dateStrToTimestamp][日期字符串转十位时间戳失败! 日期字符串: {} 失败原因: {}]", dateTime, e.getMessage(), e);
            return null;
        }
        return timestamp == 0 ? null : (int) timestamp;
    }

    /**
     * yyyy-MM-dd HH:mm:ss时间格式字符串 转 LocalDateTime
     */
    public static LocalDateTime dateStrToDate(String dateTime){
        return LocalDateTime.parse(dateTime, yyyy_MM_dd_HH_mm_ss);
    }
    
    /**
     * LocalDateTime 转 十位时间戳
     */
    public static Long timeToTimestamp(LocalDateTime dateTime){
        if (dateTime == null) {
            return 0L;
        }
        return dateTime.toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * 获取当前时间戳 (10位数, 精确到秒)
     * 注: 只会存在10位, 不用担心精度缺失
     */
    public static int currentSeconds(){
        return (int) DateUtil.currentSeconds();
    }

}
