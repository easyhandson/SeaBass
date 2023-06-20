package cn.com.seabase.framework.mybatis.core.generator;

import lombok.Data;

/**
 * 数据库连接信息
 */
@Data
public class GeneratorInfo {
    
    private String outputDir;
    
    /** 数据库url */
    private String url;
    
    /** 数据库用户名 */
    private String username;
    
    /** 数据库密码 */
    private String password;
    
}
