package cn.com.seabase.framework.common.exception.code;

/**
 * 错误码枚举类
 */
public enum ErrorCode implements Code {

    @Error(msg = "ok")
    SUCCESS,

    @Error(msg = "系统错误")
    ER0001,
    
    @Error(msg = "暂不支持该类型校验")
    ER0002,


    // ========== 客户端错误段 ==========
    @Error(msg = "请求参数不正确")
    ER0400,
    // @Error(msg = "账号未登录")
    ER0401,
    @Error(msg = "没有该操作权限")
    ER0403,
    @Error(msg = "请求未找到")
    ER0404,
    @Error(msg = "请求方法不正确")
    ER0405,
    @Error(msg = "请求失败，请稍后重试")
    ER0423,
    @Error(msg = "请求过于频繁，请稍后重试")
    ER0429,

    // ========== 服务端错误段 ==========
    @Error(msg = "系统异常")
    ER0500,

    
    // ========== Feign 异常 ==========
    @Error(msg = "test feign error, message: {}")
    ER0600,
    
    @Error(msg = "预留错误码")
    ER9999;


}
