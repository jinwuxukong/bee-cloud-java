package com.hutu.modules.admin.upms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author generator
 * @since 2019-08-16
 */
@Data
@Accessors(chain = true)
@TableName("t_common_dictionary")
public class Dictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 字典类型值表达式
     */
    private String typeKey;
    /**
     * 字典值表达式
     */
    @TableField("`key`")
    private Integer key;
    /**
     * 字典值英文名称
     */
    private String valueEN;
    /**
     * 字典值中文名称
     */
    private String valueCn;
    /**
     * 扩展字段
     */
    private String extDesc;
    /**
     * 排序
     */
    private Integer orders;
    /**
     * 创建人名称
     */
    @TableField(fill = FieldFill.INSERT)
    private String createName;
    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateName;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateId;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 逻辑删除标记(已删除：1，未删除：0)
     */
    @TableLogic
    private Integer isDeleted;


}
