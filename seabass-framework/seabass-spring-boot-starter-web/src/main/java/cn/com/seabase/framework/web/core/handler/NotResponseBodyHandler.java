package cn.com.seabase.framework.web.core.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 告知 GlobalResponseBodyHandler 返回结果不需要全局返回信息统一处理
 * 
 * 例如存在场景只需要返回一个 "success" 字符串, 那么就可以贴上该注解, 避免封装成 CommonResult 返回
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotResponseBodyHandler {
}
