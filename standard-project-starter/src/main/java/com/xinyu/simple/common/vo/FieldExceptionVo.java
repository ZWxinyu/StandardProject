package com.xinyu.simple.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *@Author xinyu
 *@Description 接口校验字段异常vo实体类
 *@Date 17:31 2019/12/17
 **/
@Data
@ApiModel(description = "字段异常信息")
public class FieldExceptionVo {
    @ApiModelProperty("字段名")
    private String fieldName;
    @ApiModelProperty("该字段出错信息")
    private String msg;

    public FieldExceptionVo(String fieldName, String msg) {
        this.fieldName = fieldName;
        this.msg = msg;
    }
}
