package cn.com.seabase.example.helper.generator;

import cn.com.seabase.framework.mybatis.core.generator.AbstractSqlAutoGenerator;
import cn.com.seabase.framework.mybatis.core.generator.GeneratorInfo;

/**
 * 示例自动生成 Controller / Service / Mapper / Mapper.xml
 */
public class ExampleSqlAutoGenerator extends AbstractSqlAutoGenerator {
    
    public String author() {
        return "gbk";
    }

    public String addTablePrefix() {
        return null;
    }

    public String parent() {
        return "cn.com.seabase.example.helper";
    }

    public String datasource() {
        return "seabass_user";
    }

    public static void main(String[] args) {
        ExampleSqlAutoGenerator generator = new ExampleSqlAutoGenerator();
        GeneratorInfo generatorInfo = generator.autoGeneratorInfo();
        generator.generator(generatorInfo);
    }
    
}
