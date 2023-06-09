package cn.com.seabase.framework.common.exception.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 错误码接口, 可实现该接口来自定义自己的错误枚举类, 但必须得遵守规则! ! !
 * 注: 利用多态来区分多个errorCode, 避免一个ErrorCode里太多错误码
 */
public interface Code {

    /** 日志 */
    Logger logger = LoggerFactory.getLogger(Code.class);
    
    /** 错误码提示模板 */
    ConcurrentMap<String, String> MESSAGES = new ConcurrentHashMap<>();

    /** 枚举字段名称 */
    String name();
    
    /**
     * 获取错误码, 默认就是枚举字段名称
     * 统一定义: 两位业务字符 + 四位数字
     * 示例: ER2222, BS0400, AM0401 等
     * 注: 因为枚举字段名称不能以数字开头, 所以如果有场景需要错误码为全数字, 请在错误码枚举类中重写该方法
     * */
    default String getCode() {
        return this.name();
    }

    /**
     * 获取错误信息, 由@Error注解标注错误信息
     * @see Error
     * */
    default String getMsg() {
        Error error = null;
        String code = this.name();
        try {
            error = this.getClass().getField(code).getAnnotation(Error.class);
        } catch (NoSuchFieldException e) {
            String codeStr = this.getClass().getSimpleName() + "." + code;
            logger.error("[Code.getMsg][ErrorCode error! Not found this code: {}]", codeStr, e);
        }
        
        if (error == null) {
            return "系统繁忙";
        }
        return error.fromCache() ? this.getMsgFromCache(code, error.msg()) : error.msg();
    }

    /**
     * 获取错误信息
     * 注: 这个是兼容了动态错误码进行的处理
     * @param code 错误码的编号
     * @param defaultMsg 默认错误码的提示,当 MESSAGES 不存在时,就返回 defaultMsg
     */
    default String getMsgFromCache(String code, String defaultMsg) {
        String msg = MESSAGES.get(code);
        if (null != msg && !msg.isBlank()) {
            return msg; 
        }
        return defaultMsg;
    }
    
}
