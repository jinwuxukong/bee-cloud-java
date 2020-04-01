package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 入会详情
 * </p>
 *
 * @author generator
 * @since 2019-11-18
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("入会详情")
@TableName("t_upms_initiation_info")
public class InitiationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 姓名
     */
	@ApiModelProperty("姓名")
	private String name;
    /**
     * 手机
     */
	@ApiModelProperty("手机")
	private String phone;
    /**
     * 邮箱
     */
	@ApiModelProperty("邮箱")
	private String email;
    /**
     * 入会理由
     */
	@ApiModelProperty("入会理由")
	private String initiationReason;
	/**
	 * 表单id
	 */
	private Integer templateId;
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


}
