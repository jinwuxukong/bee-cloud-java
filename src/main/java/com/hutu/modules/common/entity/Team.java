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
 * 医生团队表
 * </p>
 *
 * @author generator
 * @since 2019-12-25
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("医生团队表")
@TableName("t_followup_team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * 团队名字
     */
	@ApiModelProperty("团队名字")
	private String name;
    /**
     * 团队类型（1院内，2院外）
     */
	@ApiModelProperty("团队类型（1院内，2院外）")
	private Integer type;
    /**
     * 团队类型
     */
	@ApiModelProperty("团队类型")
	private String typeShow;
    /**
     * 团队拥有者id
     */
	@ApiModelProperty("团队拥有者id")
	private Integer ownerId;
    /**
     * 团队拥有者id
     */
	@ApiModelProperty("团队拥有者id")
	private String ownerName;
    /**
	 * 管理员id集合
	 */
	@ApiModelProperty("管理员id集合")
	private String managerIds;

	@ApiModelProperty("团队头像")
	private String avatar;

	@ApiModelProperty("团队描述")
	private String description;

	@ApiModelProperty("团队寄语")
	private String teamNote;

	@ApiModelProperty("擅长疾病，多个逗号隔开")
	private String disease;

	@ApiModelProperty("擅长手术，多个逗号隔开")
	private String operation;

	@ApiModelProperty("所在医院")
	private String hospital;

	@ApiModelProperty("医院等级")
	private String hospitalLevel;

	@ApiModelProperty("所在医院科室")
	private String department;

	@ApiModelProperty("收藏次数")
	private Integer likeCount;

	@ApiModelProperty("需要处理的加团申请数量")
	private Integer applyCount;

	@ApiModelProperty("审核人id")
	private Integer verifyId;

	@ApiModelProperty("审核人的名字")
	private String verifyName;

	@ApiModelProperty("审核时间")
	private LocalDateTime verifyTime;

	@ApiModelProperty("审核状态(0待审核，1审核通过，2审核不通过)")
	private Integer verifyStatus;

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
