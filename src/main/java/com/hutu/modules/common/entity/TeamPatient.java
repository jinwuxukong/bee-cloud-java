package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 医生团队患者关系中间表
 * </p>
 *
 * @author generator
 * @since 2019-12-25
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("医生团队患者关系中间表")
@TableName("t_followup_team_patient")
public class TeamPatient implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * 医生团队表id
     */
	@ApiModelProperty("医生团队表id")
	private Integer teamId;
    /**
     * 患者id
     */
	@ApiModelProperty("患者id")
	private Integer patientId;
    /**
     * 患者名称
     */
	@ApiModelProperty("患者名称")
	private String patientName;
    /**
     * 负责医生用户表id
     */
	@ApiModelProperty("负责医生用户表id")
	private String doctorName;
    /**
     * 负责医生用户表id
     */
	@ApiModelProperty("负责医生用户表id")
	private Integer doctorUserId;
    /**
     * 是否关注团队（0否，1是）
     */
	@ApiModelProperty("是否关注团队（0否，1是）")
	private Integer isBinding;
    /**
     * 是否有计划
     */
	@ApiModelProperty("是否有计划")
	private Integer isPlan;
    /**
     * 创建人名称
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人名称")
	private String createName;
    /**
     * 创建人ID
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人ID")
	private Integer createId;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("")
	private String updateName;
    /**
     * 更新人id
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人id")
	private Integer updateId;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("")
	private LocalDateTime updateTime;
    /**
     * 逻辑删除标识
     */
	@ApiModelProperty("逻辑删除标识")
    @TableLogic
	private Integer isDeleted;


}
