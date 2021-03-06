package com.xinyu.simple.web.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinyu.simple.common.utils.ValidateUtil;
import com.xinyu.simple.common.vo.ValidationResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *@Author xinyu
 *@Date 10:29 2019/12/18
 *@Description  字典表vo实体类
 *  注意：
 *      ①时间类型LocalDateTime,需要使用逐渐JsonFormat进行格式转换
 **/
@Data
//@Builder 可设置当前类使用创建者模式
@ApiModel(value = "数据字典返回vo类",description = "数据字典")
public class DictCodeVo implements Serializable{

    private static final long serialVersionUID = 3285117054927403319L;
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("字典表编码")
    private String dictCode;

    @ApiModelProperty("字典名")
    private String dictName;

    @ApiModelProperty("字典编码长度")
    private Short codeLen;

    @ApiModelProperty("是否树形")
    private Short isTree;

    @ApiModelProperty("备注")
    private String memo;

    @ApiModelProperty("排序值，一般值越小，越靠前")
    private Short sortOrder;

    @ApiModelProperty("创建人")
    private Long createBy;

    @ApiModelProperty("更新人")
    private Long updateBy;

    @ApiModelProperty("创建时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")  //配置了统一转换处理（webConfiguration.java）
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("版本号")
    private Short version;
}
