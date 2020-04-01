package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 表单结果表
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */
@Data
@Accessors(chain = true)
@TableName("t_form_result")
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 模板id
     */
    private Integer templateId;
    /**
     * 调查记录表id
     */
    private Integer surveyId;
    /**
     * 数据绑定唯一标识
     */
    private String code;
    /**
     * 模板名称
     */
    private String value;
    /**
     * 名字
     */
    private String name;
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

    public Result() {
    }

    public Result(Integer surveyId, Integer templateId, String code, String value) {
        this.surveyId = surveyId;
        this.templateId = templateId;
        this.code = code;
        this.value = value;
    }
}
