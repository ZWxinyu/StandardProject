package com.xinyu.simple.web.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 *@Author xinyu
 *@Description  查询传参form类
 *@Date 10:43 2019/12/18
 **/
@Data
@ApiModel(value="查询传参form类",description = "数据字典")
public class QueryForm {

    @ApiModelProperty(value="字典编码",required = true)
    @NotNull
    private String dictCode;

    @ApiModelProperty(value="创建时间")
    private LocalDateTime createDate;
}
