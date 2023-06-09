package cn.com.seabase.framework.common.enums.example;

import cn.com.seabase.framework.common.enums.ICommonEnum;
import cn.com.seabase.framework.common.exception.CommonException;
import cn.com.seabase.framework.common.exception.code.ErrorCode;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

/**
 * 枚举类demo, 后续有优化可改进
 */
@Getter
@AllArgsConstructor
public enum ExampleEnum implements ICommonEnum {

    EXAMPLE_1(1, "示例描述1", 1L),
    EXAMPLE_2(2, "示例描述2", 1L),
    EXAMPLE_3(3, "示例描述3", 1L),
    EXAMPLE_4(4, "示例描述4", 1L),
    EXAMPLE_5(5, "示例描述5", 1L);

    /** 值 */
    private final int code;
    /** 描述 */
    private final String desc;
    /** 测试多个字段是否会影响实现 CommonEnum 接口 */
    private final Long testLong;

    private static final Map<Integer, ExampleEnum> MAP = Maps.newHashMapWithExpectedSize(values().length);
    static {
        for (ExampleEnum type : ExampleEnum.values()) {
            MAP.put(type.code, type);
        }
    }

    /** 返回Optional是为了防止枚举值不存在返回null */
    public static Optional<ExampleEnum> valueOf(Integer value) {
        ExampleEnum exampleEnum = MAP.get(value);
        return Optional.ofNullable(exampleEnum);
    }

    /** 判断枚举值是否正确, 正确则返回相应枚举值 */
    public static ExampleEnum checkAndGet(Integer value) {
        ExampleEnum exampleEnum = MAP.get(value);
        if (null == exampleEnum) {
            throw new CommonException(ErrorCode.ER0001, "枚举值不存在");
        }
        return exampleEnum;
    }

}
