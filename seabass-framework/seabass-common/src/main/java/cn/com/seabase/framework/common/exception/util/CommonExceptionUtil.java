package cn.com.seabase.framework.common.exception.util;

import cn.com.seabase.framework.common.exception.CommonException;
import cn.com.seabase.framework.common.exception.code.Code;
import cn.com.seabase.framework.common.exception.code.ErrorCode;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Collection;

/**
 * 抛出异常工具类
 */
public class CommonExceptionUtil {

    public static void throwException(Code code){
        throw new CommonException(code);
    }
    
    public static void throwException(String code, String msg) {
        throw new CommonException(code, msg);
    }
    
    public static void throwException(CommonException exception){
        throw exception;
    }

    /** 抛出异常 - 可动态设置异常信息 */
    public static void throwException(Code errorCode, Object... args){
        throw new CommonException(errorCode, args);
    }

    /** null 或 true 抛出异常 */
    public static ThrowExceptionFunction isTrue(Boolean param){
        return (code) -> {
            if (null == param || Boolean.TRUE.equals(param)){
                throw new CommonException(code);
            }
        };
    }

    /** null 或 false 抛出异常 */
    public static ThrowExceptionFunction isFalse(Boolean param) {
        return (code) -> {
            if (null == param || Boolean.FALSE.equals(param)){
                throw new CommonException(code);
            }
        };
    }

    /** null 或 空字符串 抛出异常 */
    public static ThrowExceptionFunction isEmptyStr(String str){
        return (code) -> {
            if (StrUtil.isEmpty(str)){
                throw new CommonException(code);
            }
        };
    }

    /** null 或 空字符串 或 全空格 抛出异常 */
    public static ThrowExceptionFunction isBlankStr(String str){
        return (code) -> {
            if (StrUtil.isBlank(str)){
                throw new CommonException(code);
            }
        };
    }

    /** null 抛出异常 */
    public static ThrowExceptionFunction isNull(Object object){
        return (code) -> {
            if (null == object){
                throw new CommonException(code);
            }
        };
    }
    
    /** null 或 空集合 抛出异常 */
    public static ThrowExceptionFunction isEmptyList(Collection<?> collection){
        return (code) -> {
            if (collection == null || collection.isEmpty()){
                throw new CommonException(code);
            }
        };
    }

    /** 都为null 或者 非null且equals相等 抛出异常 */
    public static ThrowExceptionFunction equalsTrue(Object o1, Object o2) {
        return (code) -> {
            if (ObjectUtil.equal(o1, o2)){
                throw new CommonException(code);
            }
        };
    }

    /** 其中一个为null 或者 都非null且equals不相等 抛出异常 */
    public static ThrowExceptionFunction equalsFalse(Object o1, Object o2) {
        return (code) -> {
            if (ObjectUtil.notEqual(o1, o2)){
                throw new CommonException(code);
            }
        };
    }

    /**
     * 基本数据类型封装类校验 (为null或小于等于0抛出异常)
     * 注: 只支持 Integer, Long, Float, Double, Short, Byte !!!
     *  */
    public static ThrowExceptionFunction notGtZero(Number number){
        return (code) -> {
            if (null == number) {
                throw new CommonException(code);
            }

            if (number instanceof Integer) {
                if (number.intValue() <= 0) {
                    throw new CommonException(code);
                }
                return;
            }

            if (number instanceof Long) {
                if (number.longValue() <= 0) {
                    throw new CommonException(code);
                }
                return;
            }

            if (number instanceof Double) {
                if (number.doubleValue() <= 0) {
                    throw new CommonException(code);
                }
                return;
            }
            
            if (number instanceof Float) {
                if (number.floatValue() <= 0) {
                    throw new CommonException(code);
                }
                return;
            }


            if (number instanceof Short) {
                if (number.shortValue() <= 0) {
                    throw new CommonException(code);
                }
                return;
            }

            if (number instanceof Byte) {
                if (number.byteValue() <= 0) {
                    throw new CommonException(code);
                }
                return;
            }

            throw new CommonException(ErrorCode.ER0002);
        };
    }

    /**
     * 异常抛出回调接口
     **/
    @FunctionalInterface
    public interface ThrowExceptionFunction {
        /**
         * 抛出异常信息
         * @param code 错误码
         */
        void throwMessage(Code code);
    }

}
