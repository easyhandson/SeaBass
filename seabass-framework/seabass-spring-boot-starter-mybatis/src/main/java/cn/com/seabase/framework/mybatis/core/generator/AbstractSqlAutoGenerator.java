package cn.com.seabase.framework.mybatis.core.generator;

import cn.com.seabase.framework.common.exception.CommonException;
import cn.com.seabase.framework.common.exception.code.ErrorCode;
import cn.com.seabase.framework.common.exception.util.CommonExceptionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Collections;
import java.util.Properties;

/**
 * MP 快速生成抽象类
 * 
 * 传送门: https://baomidou.com/pages/981406/#%E6%95%B0%E6%8D%AE%E5%BA%93%E9%85%8D%E7%BD%AE-datasourceconfig
 */
public abstract class AbstractSqlAutoGenerator {

    /** 作者 */
    public abstract String author();

    /** 过滤表前缀 */
    public abstract String addTablePrefix();
    
    /** 快速生成java文件所在包路径 */
    public abstract String parent();

    /** 数据源 (因为我用的是多数据源, 所以需要加这个) */
    public abstract String datasource();

    /** 读取数据库配置所在 yaml 文件的名称 (这里是默认, 可被重写) */
    public String yaml() {
        return "application-local.yml";
    }
    
    /** 生成所在的文件夹 */
    public String outputDir() {
        return null;
    }

    /** 数据库用户名称 */
    public String username() {
        return null;
    }

    /** 数据库密码 */
    public String password() {
        return null;
    }
    
    /** 
     * 根据表自动生成
     * */
    public void generator() {
        // 获取生成相关信息
        GeneratorInfo generatorInfo = this.getGeneratorInfo();
        
        // 数据库连接用户名
        String username = username();
        if (null == username || username.isBlank()) {
            username = generatorInfo.getUsername();
        }
        
        // 数据库连接用户密码
        String password = password();
        if (null == password || password.isBlank()) {
            password = generatorInfo.getPassword();
        }
        
        // 生成文件所在位置, (javaDir: controller,service,entity,mapper位置) (mapperDir: mapper.xml 文件生成位置)
        String outputDir = generatorInfo.getOutputDir();
        String javaDir = outputDir + "/src/main/java";
        String mapperDir = outputDir + "/src/main/resources/mapper";
        
        // 快速创建表相关类(实体类, Service, Mapper...)
        FastAutoGenerator.create(generatorInfo.getUrl(), username, password)
                // 全局配置
                .globalConfig((scanner, builder) -> builder
                        .author(this.author())  // 作者
                        .enableSwagger()    // 开启swagger
                        .disableOpenDir()   // 禁止打开输出目录
                        .dateType(DateType.TIME_PACK)   // java8 新的时间类型  (也可以用 DateType.ONLY_DATE 就会将时间类型都转换为 java.util.date )
                        .outputDir(javaDir))  // 指定输出目录
                // 包配置 (生成包位置)
                .packageConfig((scanner, builder) -> builder
                        .parent(this.parent())  // 包路径
                        .pathInfo(Collections.singletonMap(OutputFile.xml, mapperDir)))  // // 设置mapperXml生成路径
                // 策略配置 (可配置entity, controller, service, mapper策略)
                .strategyConfig((scanner, builder) -> builder
                        .addInclude(scanner.apply("请输入表名，多个英文逗号分隔"))    // 需要自动生存的表名称
                        .addTablePrefix(this.addTablePrefix())  // 增加过滤表前缀 
                        .controllerBuilder()    // Controller 策略配置, 如下
                        .enableRestStyle()      // 开启生成@RestController 控制器
                        .entityBuilder()    // Entity 策略配置, 如下
                        // .superClass(Object.class)   // 设置父类
                        .enableLombok()     // 开启 lombok 模型
                        .enableChainModel()     // 开启链式模型
                        .naming(NamingStrategy.underline_to_camel)      // 数据库表映射到实体的命名策略 (下划线转驼峰)
                        .columnNaming(NamingStrategy.underline_to_camel)        // 数据库表字段映射到实体的命名策略 (下划线转驼峰)
                        .enableRemoveIsPrefix()     // 开启 Boolean 类型字段移除 is 前缀
                        .build())
                .execute();
    }

    // 获取生成文件位置
    public GeneratorInfo getGeneratorInfo() {
        // 获取yaml中的配置
        YamlPropertiesFactoryBean yamlProFb = new YamlPropertiesFactoryBean();
        yamlProFb.setResources(new ClassPathResource(this.yaml()));
        Properties properties = yamlProFb.getObject();
        if (ObjectUtil.isNull(properties)) {
            CommonExceptionUtil.throwException(new CommonException());
        }

        GeneratorInfo generatorInfo = new GeneratorInfo();
        generatorInfo.setOutputDir(autoGetOutputDir());
        // 获取数据库配置 (这里是多数据源, 可模仿 datasource() 方法进行自定义配置)
        String url = (String) properties.get("spring.datasource.dynamic.datasource." + this.datasource() +".url");
        generatorInfo.setUrl(url);
        String username = (String) properties.get("spring.datasource.dynamic.datasource." + this.datasource() + ".username");
        generatorInfo.setUsername(username);
        String password = (String) properties.get("spring.datasource.dynamic.datasource." + this.datasource() + ".password");
        generatorInfo.setPassword(password);
        
        return generatorInfo;
    }

    /** 获取生成文件夹 */
    private String autoGetOutputDir() {
        // 若有传路径则不再自动获取
        String outputDir = outputDir();
        if (null != outputDir && !outputDir.isBlank()) {
            return outputDir;
        }
        // 自动获取生成所在的文件夹
        String path = getClass().getResource("").getPath();
        if (null == path || path.isBlank()) {
            throw new CommonException(ErrorCode.ER0001, "自动获取文件夹失败");
        }
        int indexOf = path.indexOf("/target");
        return path.substring(0, indexOf);
    }
    
    
}
