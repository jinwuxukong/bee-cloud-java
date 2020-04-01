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
 * 医生团队成员表
 * </p>
 *
 * @author generator
 * @since 2019-12-25
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("医生团队成员表")
@TableName("t_followup_team_member")
public class TeamMember implements Serializable {

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
     * 组员id（user表主键）
     */
	@ApiModelProperty("组员id（user表主键）")
	private Integer userId;
    /**
     * 队员名字
     */
	@ApiModelProperty("队员名字")
	private String name;

	@ApiModelProperty("科室")
	private String department;

	@ApiModelProperty("职称")
	private String jobType;

	@ApiModelProperty("审核人id")
	private String verifyId;

	@ApiModelProperty("审核人的名字")
	private String verifyName;

	@ApiModelProperty("审核时间")
	private String verifyTime;

	@ApiModelProperty("审核状态(0待审核，1审核通过，2审核不通过)")
	private Integer verifyStatus;
	/**
     * 成员类型（1普通，2管理员，3拥有者）
     */
	@ApiModelProperty("成员类型（1普通，2管理员，3拥有者）")
	private Integer type;
    /**
     * 成员类型（1普通，2管理员，3拥有者）
     */
	@ApiModelProperty("成员类型（1普通，2管理员，3拥有者）")
	private String typeShow;
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
