package cn.com.seabase.framework.common.enums;

/**
 * 枚举类描述接口
 */
public interface IDescBaseEnum {

    /** 枚举字段名称 */
    String name();

    /** 获取 枚举描述 */
    String getDesc();
    
    /** 获取 枚举字段名称 */
    default String getName(){
        return name();
    }
    
}
