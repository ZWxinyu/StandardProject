package com.xinyu.simple.web.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *@Author xinyu
 *@Description  字典表vo实体类
 *@Date 10:29 2019/12/18
 **/
@Data
//@Builder 可设置当前类使用创建者模式
@ApiModel(value = "数据字典返回vo类",description = "数据字典")
public class DictCodeVo{
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("字典表编码")
    private String dictCode;

    @ApiModelProperty("字典名")
    private String dictName;

    @ApiModelProperty("字典编码长度")
    private BigDecimal codeLen;

    @ApiModelProperty("是否树形")
    private Long isTree;

    @ApiModelProperty("备注")
    private String memo;

    @ApiModelProperty("排序值，一般值越小，越靠前")
    private Long sortOrder;

    @ApiModelProperty("创建人")
    private Long createBy;

    @ApiModelProperty("更新人")
    private Long updateBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("版本号")
    private Integer version;

}
