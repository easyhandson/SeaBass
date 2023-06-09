package cn.com.seabase.framework.swagger.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

/**
 * Swagger2 自动配置类
 * 注: 来源芋道
 *
 * 在 resources 文件下的 spring.factories 文件中有配置 SwaggerAutoConfiguration
 */
@Configuration
@EnableKnife4j
public class SwaggerAutoConfiguration {

    /**
     * swagger 配置
     */
    @Bean
    @ConfigurationProperties("seabass.swagger")
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

    @Bean
    public Docket createRestApi() {
        SwaggerProperties properties = swaggerProperties();
        // 创建 Docket 对象
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(properties.isEnable())
                // 用来创建该 API 的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo(properties))
                // 设置扫描指定 package 包下的
                .select()
                .apis(basePackage(properties.getBasePackage()))
                // 接口路径过滤方案
                .paths(PathSelectors.any())
                .build()
                // .securitySchemes(securitySchemes())
                // 设置全局参数
                // .globalRequestParameters(globalRequestParameters())
                .securityContexts(securityContexts());
    }

    /** 接口文档信息 */
    private static ApiInfo apiInfo(SwaggerProperties properties) {
        return new ApiInfoBuilder()
                // 标题
                .title(properties.getTitle())
                // 描述
                .description(properties.getDescription())
                // 联络信息 (联络人, url地址, email)
                .contact(new Contact(properties.getAuthor(), null, null))
                // 版本
                .version(properties.getVersion())
                .build();
    }
    

    /**
     * 安全上下文
     *
     * @see #authorizationScopes()
     */
    private static List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build());
    }

    private static List<SecurityReference> securityReferences() {
        return Collections.singletonList(new SecurityReference(HttpHeaders.AUTHORIZATION, authorizationScopes()));
    }

    private static AuthorizationScope[] authorizationScopes() {
        return new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")};
    }


    /**
     * 解决springboot2.6 和springFox不兼容问题
     * 传送门: https://blog.csdn.net/weixin_49523761/article/details/122305980
     * 注: 还是得加上
     * spring:
     *   mvc:
     *     pathmatch:
     *       matching-strategy: ant_path_matcher
     *  不然接口就不会显示
     */
    @Bean
    public static BeanPostProcessor springFoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringFoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringFoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }


}
