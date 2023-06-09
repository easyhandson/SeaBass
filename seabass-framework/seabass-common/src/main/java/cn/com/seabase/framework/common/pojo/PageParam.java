package cn.com.seabase.framework.common.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@ApiModel("分页参数")
public class PageParam implements Serializable {
    private static final long serialVersionUID = 1;
    
    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    @ApiModelProperty(value = "页码，从 1 开始", required = true,example = "1")
    private Integer pageNo = PAGE_NO;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    @Max(value = 100, message = "页码最大值为 100")
    @ApiModelProperty(value = "每页条数，最大值为 100", required = true, example = "10")
    private Integer pageSize = PAGE_SIZE;

}
