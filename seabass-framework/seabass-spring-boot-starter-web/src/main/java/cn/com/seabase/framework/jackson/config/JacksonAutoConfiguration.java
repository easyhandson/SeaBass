package cn.com.seabase.framework.jackson.config;

import cn.com.seabase.framework.jackson.core.Java8TimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson 配置类
 * 注: 用来全局统一配置一些前端段数据类型的转化, 如 LocalDateTime -(后 -> 前)-> 时间戳(毫秒) , 时间戳(毫秒) -(前 -> 后)-> LocalDateTime
 * 注: 如果需要, 这里还可以自定义相应的类型转换! ! ! 模仿 LocalDateTimeSerializer 和 LocalDateTimeDeserializer 去写即可, 或者通过注解方式...
 */
@Slf4j
@Configuration
public class JacksonAutoConfiguration {

    /**
     * 1. 新增LocalDateTime序列化、反序列化规则
     * */
    @Bean
    public BeanPostProcessor objectMapperBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (!(bean instanceof ObjectMapper)) {
                    return bean;
                }
                ObjectMapper objectMapper = (ObjectMapper) bean;
                objectMapper.registerModules(new Java8TimeModule());
                // 若POJO对象的属性值为null，序列化时不进行显示
                // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                log.info("[JacksonAutoConfiguration-objectMapperBeanPostProcessor][初始化 jackson 自动配置]");
                return bean;
            }
        };
    }

}
