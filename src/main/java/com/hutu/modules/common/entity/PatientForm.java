package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 直接随访患者表单表
 * </p>
 *
 * @author generator
 * @since 2019-10-16
 */
@Data
@Accessors(chain = true)
@TableName("t_followup_patient_form")
public class PatientForm implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 0 未推送，1 已推送，2，已填写/已阅
     */
	private Integer status;
	/**
	 * 表单id
	 */
	private Integer templateId;
	/**
	 * 表单模板类型
	 */
	private Integer formType;
	/**
	 * 表单模板类型名称
	 */
	private String formTypeShow;
    /**
     * 表单名称
     */
	private String name;
    /**
     * 表单内容
     */
	private String content;
	/**
	 * 量表答案
	 */
	private String answer;
    /**
     * 类型
     */
	private Integer type;
	/**
	 * 类型展示
	 */
	private String typeShow;
    /**
     * 患者表id
     */
	private Integer patientId;
    /**
     * 患者姓名
     */
	private String patientName;
	/**
	 * 患者头像
	 */
	private String patientPhoto;
	/**
	 * 通知状态
	 */
	private Integer informState;
	/**
	 * 通知状态显示
	 */
	private String informStateShow;
    /**
     * 创建者id
     */
	@TableField(fill = FieldFill.INSERT)
	private Integer createId;
    /**
     * 创建者名称
     */
	@TableField(fill = FieldFill.INSERT)
	private String createName;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	/**
	 * 更新人名称
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateName;
	/**
	 * 更新人ID
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Integer updateId;
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
    @TableLogic
	private Integer isDeleted;

	/**
	 * 手机号
	 */
	private String phone;


}
