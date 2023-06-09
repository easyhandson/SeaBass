package cn.com.seabase.framework.common.exception.code;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 错误码注解
 *
 * 通过枚举类 + 该注解可以方便,简洁的写我们自定义的错误码
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Error {

    /** 错误信息 */
    String msg() default "系统异常";

    /** 是否来自于缓存 */
    boolean fromCache() default true;

}
