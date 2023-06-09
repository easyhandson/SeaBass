package cn.com.seabase.framework.common.enums;

/**
 * 枚举类公共接口
 */
public interface ICommonEnum extends ICodeBaseEnum, IDescBaseEnum {
    
    /**
     * 匹配 枚举字段名称 是否相同 (不再需要代码层使用equals判断了, 拿来即用!!!)
     * 
     * @param name 字段
     * @return true-相同, false-不相同
     */
    default boolean matchName(String name){
        if (null == name || name.isBlank()) {
            return false;
        }
        return name.equals(this.getName());
    }
    
    /**
     * 匹配 枚举code 是否相同  (不再需要代码层使用equals判断了, 拿来即用!!!)
     * 
     * @param code 枚举code
     * @return true-相同, false-不相同
     */
    default boolean matchCode(Integer code){
        if (null == code){
            return false;
        }
        return code.equals(this.getCode());
    }
    default boolean matchCode(Long code){
        if (null == code){
            return false;
        }
        return code.intValue() == this.getCode();
    }
    
}
