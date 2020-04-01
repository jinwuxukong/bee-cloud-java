package com.hutu.modules.app.center.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class ScaleTypeVo implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 表单名称
     */
	private String name;
	/**
	 * 患者表id
	 */
	private Integer patientId;
    /**
     * 患者姓名
     */
	private String patientName;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 量表标签
	 */
	private Integer formType;
	/**
	 * 量表标签显示
	 */
	private String formTypeShow;
	/**
	 * 患者头像
	 */
	private String patientPhoto;
	/**
	 * 随访类型展示
	 */
	private String scaleTypeShow;
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
	/**
	 * 是否微信已读0.未读1已读
	 */
	@ApiModelProperty("是否微信已读0.未读1已读")
	private int isSendWeChat;
	/**
	 * 通知状态0.未通知1.已通知
	 */
	@ApiModelProperty("通知状态0.未通知1.已通知")
	private int informState;
	/**
	 * 所在团队表id
	 */
	@ApiModelProperty("所在团队表id")
	private Integer teamId;
	/**
	 * 团队名称
	 */
	@ApiModelProperty("团队名称")
	private String teamName;

}
