package cn.com.seabase.framework.common.pojo;

import cn.com.seabase.framework.common.exception.CommonException;
import cn.com.seabase.framework.common.exception.code.Code;
import cn.com.seabase.framework.common.exception.code.ErrorCode;
import cn.com.seabase.framework.common.util.str.StrUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回实体类
 */
@Getter
@Setter
@ToString
public class CommonResult<T> implements Serializable {
    private static final long serialVersionUID = -4382241318920259758L;

    /**
     * 结果码
     * @see ErrorCode#getCode()
     * */
    @ApiModelProperty(value = "结果码: SUCCESS:成功")
    private String code = "SUCCESS";

    /**
     * 返回信息
     * @see ErrorCode#getMsg()
     * */
    @ApiModelProperty(value = "提示信息")
    private String msg = "ok";

    /** 返回内容 */
    @ApiModelProperty(value = "数据")
    private T data;
    
    private CommonResult() {
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult<>();
    }
    
    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> responseModel = new CommonResult<>();
        responseModel.setData(data);
        return responseModel;
    }

    @JsonIgnore // 避免 jackson 序列化
    public boolean isSuccess() {
        return this.isSuccess(this.code);
    }
    
    @JsonIgnore // 避免 jackson 序列化
    public boolean isSuccess(String code) {
        return ErrorCode.SUCCESS.getCode().equals(code);
    }

    public static <T> CommonResult<T> error(Code Code) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setMsg(Code.getMsg());
        commonResult.setCode(Code.getCode());
        return commonResult;
    }

    public static <T> CommonResult<T> error(Code code, Object... args) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setCode(code.getCode());
        commonResult.setMsg(StrUtils.format(code.getMsg(), args));
        return commonResult;
    }

    public static <T> CommonResult<T> error(CommonException exception) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setCode(exception.getCode());
        commonResult.setMsg(exception.getMessage());
        return commonResult;
    }

    public static <T> CommonResult<T> error(String errorCode, String message) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setMsg(message);
        commonResult.setCode(errorCode);
        return commonResult;
    }
    
    /** 抛错以及返回自定义Msg */
    public static <T> CommonResult<T> errorOtherMsg(Code Code, String message) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setMsg(message);
        commonResult.setCode(Code.getCode());
        return commonResult;
    }

    /** 返回错误码以及data数据 */
    public static <T> CommonResult<T> errorWithData(Code Code, T data, String message) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setData(data);
        commonResult.setMsg(message);
        commonResult.setCode(Code.getCode());
        return commonResult;
    }
    
    /** 判断是否有异常。如果有，则抛出 {@link CommonException} 异常 */
    public void checkError() {
        if (this.isSuccess()) {
            return;
        }
        throw new CommonException(code, msg);
    }
    
    /**
     * 判断是否有异常。如果有，则抛出 {@link CommonException} 异常
     * 如果没有，则返回 {@link #data} 数据
     */
    @JsonIgnore // 避免 jackson 序列化
    public T getAndCheckData() {
        this.checkError();
        return data;
    }

}
