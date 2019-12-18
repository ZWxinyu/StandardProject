package com.xinyu.simple.biz.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author xinyu
 * @since 2019-12-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_dict_code")
public class DictCode extends Model<DictCode> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value="id",type= IdType.AUTO)
    private Long id;

    /**
     * 字典表编码
     */
    @TableField("dictCode")
    private String dictCode;

    /**
     * 字典名
     */
    @TableField("dictName")
    private String dictName;

    /**
     * 字典编码长度
     */
    @TableField("codeLen")
    private BigDecimal codeLen;

    /**
     * 是否树形
     */
    @TableField("isTree")
    private Long isTree;

    /**
     * 备注
     */
    private String memo;

    /**
     * 排序值，一般值越小，越靠前
     */
    @TableField("sortOrder")
    private Long sortOrder;

    /**
     * 创建人
     */
    @TableField("createBy")
    private Long createBy;

    /**
     * 更新人
     */
    @TableField("updateBy")
    private Long updateBy;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private LocalDateTime updateTime;

    /**
     * 版本号
     */
    @TableField("VERSION")
    private Integer version;


}
