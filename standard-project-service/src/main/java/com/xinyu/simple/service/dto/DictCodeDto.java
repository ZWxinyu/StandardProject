package com.xinyu.simple.service.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *@Author xinyu
 *@Description  字典表dto实体类
 *@Date 10:29 2019/12/18
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class DictCodeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 字典表编码
     */
    private String dictCode;

    /**
     * 字典名
     */
    private String dictName;

    /**
     * 字典编码长度
     */
    private Short codeLen;

    /**
     * 是否树形
     */
    private Short isTree;

    /**
     * 备注
     */
    private String memo;

    /**
     * 排序值，一般值越小，越靠前
     */
    private Short sortOrder;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 版本号
     */
    private Short version;

}
