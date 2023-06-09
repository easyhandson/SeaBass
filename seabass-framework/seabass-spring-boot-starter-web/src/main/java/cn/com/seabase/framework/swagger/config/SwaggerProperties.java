package cn.com.seabase.framework.swagger.config;

import lombok.Data;

/**
 * Swagger 配置属性
 * 注: 来源芋道
 *
 * ConfigurationProperties: 将配置文件转换成类对象
 */
@Data
public class SwaggerProperties {

    /**
     * 是否开启swagger
     */
    private boolean enable;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 作者
     */
    private String author;

    /**
     * 版本
     */
    private String version;

    /**
     * 扫描的包
     */
    private String basePackage;

}
