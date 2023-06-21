package cn.com.seabase.framework.mybatis.core.generator;

import lombok.Data;

/**
 * 数据库连接信息
 */
@Data
public class GeneratorInfo {
    
    /** 数据库url */
    private String url;
    
    /** 数据库用户名 */
    private String username;
    
    /** 数据库密码 */
    private String password;
    
    /** 数据源 */
    private String datasource;
    
    /** controller,service,entity,mapper 等生成的位置 */
    private String javaDir;
    
    /** mapper.xml 生成的位置 */
    private String xmlMapperDir;
    
}
