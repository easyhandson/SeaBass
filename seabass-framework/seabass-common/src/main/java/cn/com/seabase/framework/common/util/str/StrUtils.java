package cn.com.seabase.framework.common.util.str;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * String操作Helper (可以用来补充Hutool中StrUtil不存在的方法在此类中)
 */
@Slf4j
public class StrUtils {

    /**
     * 格式化错误提示 (注: 使用String.format()方法,如果参数少于占位符会报错,因此自定义格式化错误提示)
     *
     * @param messagePattern 消息模版
     * @param params         参数
     * @return 格式化后的提示
     */
    public static String format(String messagePattern, Object... params) {
        StringBuilder sb = new StringBuilder(messagePattern.length() + 50);
        String placeholder = "{}";
        int index;
        int beginIndex = 0;
        for (Object param : params) {
            // 从i位置起第一个'{}'所在的起始位置 (-1表示不存在)
            index = messagePattern.indexOf(placeholder, beginIndex);
            if (index == -1) {
                log.error("[StrHelper-doFormat][参数过多：错误内容({})|参数({})", messagePattern, params);
                if (beginIndex == 0) {
                    return messagePattern;
                }
                sb.append(messagePattern.substring(beginIndex));
                return sb.toString();
            }
            // 切割messagePattern并拼接
            sb.append(messagePattern, beginIndex, index);
            sb.append(param);
            beginIndex = index + 2;
        }

        if (messagePattern.indexOf(placeholder, beginIndex) != -1) {
            log.error("[StrHelper-doFormat][参数过少：错误内容({})|参数({})", messagePattern, params);
        }
        sb.append(messagePattern.substring(beginIndex));
        return sb.toString();
    }

    /**
     * 截断字符串
     *
     * 注: 若字符串为空, 则返回空
     * 注: 字符串是由多个空格组成, 则返回空
     *
     * @param str 需要截断的字符串
     * @param length 截断长度
     * @return 截断后的字符串
     */
    public static String cutOffString(String str, Integer length) {
        if (null == str) {
            return null;
        }
        if (str.isBlank()) {
            return "";
        }
        int strLength = str.length();
        if (strLength <= length) {
            return str;
        }
        return StrUtil.sub(str, 0, length);
    }

}
