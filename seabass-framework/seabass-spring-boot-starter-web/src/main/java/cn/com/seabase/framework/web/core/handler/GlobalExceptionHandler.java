package cn.com.seabase.framework.web.core.handler;

import cn.com.seabase.framework.common.exception.CommonException;
import cn.com.seabase.framework.common.exception.code.ErrorCode;
import cn.com.seabase.framework.common.pojo.CommonResult;
import cn.com.seabase.framework.common.util.str.StrUtils;
import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 全局统一异常处理 (项目必备)
 *
 * 注: 捕获异常类是会捕获到当前类及其子类
 * 注: 如果多个项目存在自定义的异常类且需要自定义返回内容,那么可以继承该类,去自定义异常捕获处理! 反之不继承使用该全局异常捕获类即可
 *
 * 注意: 404异常它需要设置如下两个配置项!!!
 * 1. spring.mvc.throw-exception-if-no-handler-found 为 true
 * 2. spring.mvc.static-path-pattern 为 /statics/**
 */
@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常
     * @see CommonException
     * */
    @ExceptionHandler(CommonException.class)
    public CommonResult<?> commonExceptionHandler(CommonException ex) {
        log.error("[commonExceptionHandler][error msg: {}]", ex.toString(), ex);
        return CommonResult.error(ex);
    }

    /**
     * 处理 SpringMVC 请求参数缺失
     *
     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResult<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        log.error("[missingServletRequestParameterExceptionHandler][请求参数缺失: {}]", ex.getParameterName(), ex);
        return CommonResult.errorOtherMsg(ErrorCode.ER0400, StrUtils.format("请求参数缺失: {}", ex.getParameterName()));
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     *
     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResult<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.error("[methodArgumentTypeMismatchExceptionHandler][请求参数类型错误: {}]", ex.getMessage(), ex);
        return CommonResult.errorOtherMsg(ErrorCode.ER0400, StrUtils.format("请求参数类型错误: {}", ex.getMessage()));
    }

    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : ex.getAllErrors()) {
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
            msg.append(", ");
        }
        int length = msg.length();
        String msgStr = msg.delete(length - 2, length).toString();
        log.error("[methodArgumentNotValidExceptionHandler][请求参数不正确: {}]", msgStr, ex);
        return CommonResult.errorOtherMsg(ErrorCode.ER0400, StrUtils.format("请求参数不正确: [{}]", msgStr));
    }

    /**
     * 处理 SpringMVC 参数绑定不正确，本质上也是通过 Validator 校验
     */
    @ExceptionHandler(BindException.class)
    public CommonResult<?> bindExceptionHandler(BindException ex) {
        FieldError fieldError = ex.getFieldError();
        if (ObjectUtil.isNull(fieldError)) {
            log.error("[bindExceptionHandler][error msg: {}]", ex.getMessage(), ex);
            return CommonResult.error(ErrorCode.ER0400);
        }
        String message = fieldError.getDefaultMessage();
        log.error("[bindExceptionHandler][请求参数不正确: {}]", message, ex);
        return CommonResult.errorOtherMsg(ErrorCode.ER0400, StrUtils.format("请求参数不正确: {}", message));
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        String message = constraintViolation.getMessage();
        log.error("[constraintViolationExceptionHandler][请求参数不正确: {}]", message, ex);
        return CommonResult.errorOtherMsg(ErrorCode.ER0400, StrUtils.format("请求参数不正确: {}", message));
    }

    /**
     * 处理 SpringMVC 请求地址不存在
     *
     * 注意，它需要设置如下两个配置项：
     * 1. spring.mvc.throw-exception-if-no-handler-found 为 true
     * 2. spring.mvc.static-path-pattern 为 /statics/**
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResult<?> noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
        // 判断是否是 swagger/knife4j 无关紧要的 favicon.ico 资源, 是则忽略
        String requestURL = ex.getRequestURL();
        if (requestURL.endsWith("favicon.ico")) {
            return CommonResult.success();
        }
        log.error("[noHandlerFoundExceptionHandler][请求地址不存在: {}]", ex.getRequestURL(), ex);
        return CommonResult.errorOtherMsg(ErrorCode.ER0404, StrUtils.format("请求地址不存在: {}", ex.getRequestURL()));
    }

    /**
     * 处理 SpringMVC 请求方法不正确
     *
     * 例如说，A 接口的方法为 GET 方式，结果请求方法为 POST 方式，导致不匹配
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.error("[httpRequestMethodNotSupportedExceptionHandler][请求方法不正确: {}]", ex.getMessage(), ex);
        return CommonResult.errorOtherMsg(ErrorCode.ER0405, StrUtils.format("请求方法不正确: {}", ex.getMessage()));
    }

    /**
     * 兜底异常处理
     */
    @ExceptionHandler(Throwable.class)
    public CommonResult<?> exceptionHandler(HttpServletRequest req, Throwable ex) {
        log.error("[exceptionHandler][message: {}]", ex.getMessage(), ex);
        
        // 找到最底层的异常
        ex = this.findReallyException(ex);
        String msg = ex.getMessage();
        log.error("[exceptionHandler][Really message: {}]", msg);
        
        // 如果被包装的是自定义异常, 那么获取并抛出
        if (ex instanceof CommonException) {
            return this.commonExceptionHandler((CommonException) ex);
        }
        
        return CommonResult.error(ErrorCode.ER0001);
    }

    /**
     * 找出正确的异常(有些异常会包装好几层)
     * 抛出的异常不一定是真正的异常,可能被包装过,所以我们需要获取真正的异常,这里的获取方法不一定正确...后可改进
     */
    private Throwable findReallyException(Throwable ex) {
        // 自定义的异常
        if (ex instanceof CommonException) {
            return ex;
        }
        
        // 最底层的异常
        Throwable cause = ex.getCause();
        if (cause == null) {
            return ex;
        }
        
        return this.findReallyException(ex.getCause());
    }
    
}
