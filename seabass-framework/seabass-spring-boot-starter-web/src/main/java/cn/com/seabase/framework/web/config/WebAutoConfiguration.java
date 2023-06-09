package cn.com.seabase.framework.web.config;

import cn.com.seabase.framework.web.core.handler.GlobalExceptionHandler;
import cn.com.seabase.framework.web.core.handler.GlobalResponseBodyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * web 自动装配Config
 * */
@Configuration
public class WebAutoConfiguration {

    @Bean
    public GlobalResponseBodyHandler globalExceptionHandler() {
        return new GlobalResponseBodyHandler();
    }

    /** 全局异常处理 */
    @Bean
    public GlobalExceptionHandler globalResponseBodyHandler() {
        return new GlobalExceptionHandler();
    }

}
