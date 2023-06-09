package cn.com.seabase.framework.common.exception;

import cn.com.seabase.framework.common.exception.code.Code;
import cn.com.seabase.framework.common.exception.code.ErrorCode;
import cn.com.seabase.framework.common.exception.util.CommonExceptionUtil;
import cn.com.seabase.framework.common.util.str.StrUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 错误异常类
 */
@Slf4j
@Getter
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = -283481050484979227L;

    /** 错误码 */
    private final String code;

    /** 错误提示 */
    private final String message;

    public CommonException() {
        this(ErrorCode.ER0001);
    }   

    public CommonException(Code Code){
        this(Code.getCode(), Code.getMsg());
    }

    public CommonException(String code, String message){
        this.code = code;
        this.message = message;
    }

    /** 用来实现动态错误信息, 使用的是{}占位符 */
    public CommonException(Code Code, Object... args){
        throw new CommonException(Code.getCode(), StrUtils.format(Code.getMsg(), args));
    }

    @Override
    public String toString() {
        // 注: 这里不能用 this.code 或 this.message,要用this.getCode() 和 this.getMessage(), 这样才其他子类不会去拿当前类的值
        return StrUtils.format("[at {}] [errorCode: {}, errorMsg: {}]", this.getCaller(), this.getCode(), this.getMessage());
    }

    /** 获取调用者信息(类名,方法名,位置) */
    private String getCaller() {
        StackTraceElement[] stack = this.getStackTrace();
        if (stack.length < 1) {
            return "...";
        }

        // 一般默认是第一个, 但是由于我自己封装了一层工具类抛错, 所以当类名为工具类名称时, 获取工具类上一层的类名
        StackTraceElement ste = stack[0];
        String className = ste.getClassName();
        if (Objects.equals(className, CommonExceptionUtil.class.getName())) {
            ste = stack[1];
        }
        
        // 格式和报错栈的格式一样
        return ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
    }

}
