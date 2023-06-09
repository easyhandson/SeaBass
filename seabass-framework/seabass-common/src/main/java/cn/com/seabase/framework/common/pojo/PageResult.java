package cn.com.seabase.framework.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
@ApiModel("分页结果类")
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1;

    @ApiModelProperty("总数")
    private Long total;

    @ApiModelProperty("数据")
    private List<T> list;

    public PageResult() {
    }

    public PageResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public PageResult(Long total) {
        this.total = total;
        this.list = Collections.emptyList();
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L);
    }

    public static <T> PageResult<T> empty(Long total) {
        return new PageResult<>(total);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.list == null || this.list.isEmpty();
    }

}

