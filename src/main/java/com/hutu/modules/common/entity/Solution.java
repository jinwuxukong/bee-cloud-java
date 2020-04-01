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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 方案表
 * </p>
 *
 * @author generator
 * @since 2020-03-27
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("方案表")
@TableName("t_followup_solution")
public class Solution implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("主键")
	private Integer id;
    /**
     * 方案名称
     */
	@ApiModelProperty("方案名称")
	private String name;
    /**
     * 适用科室
     */
	@ApiModelProperty("适用科室")
	private Integer department;
    /**
     * 科室展示
     */
	@ApiModelProperty("科室展示")
	private String departmentShow;
    /**
     * 适用手术
     */
	@ApiModelProperty("适用手术")
	private Integer operation;
    /**
     * 手术展示
     */
	@ApiModelProperty("手术展示")
	private String operationShow;
    /**
     * 专业
     */
	@ApiModelProperty("专业")
	private Integer type;
    /**
     * 专业类型
     */
	@ApiModelProperty("专业类型")
	private String typeShow;
    /**
     * 共享范围（个人0，团队1，平台2）
     */
	@ApiModelProperty("共享范围（个人0，团队1，平台2）")
	private Integer scope;
    /**
     * （个人0，团队1，平台2）
     */
	@ApiModelProperty("（个人0，团队1，平台2）")
	private String scopeShow;
    /**
     * 介绍
     */
	@ApiModelProperty("介绍")
	private String introduce;
    /**
     * 状态（禁用0，启用1）
     */
	@ApiModelProperty("状态（禁用0，启用1）")
	private Integer status;
    /**
     * 审核人id
     */
	@ApiModelProperty("审核人id")
	private Integer verifyId;
    /**
     * 审核人的名字
     */
	@ApiModelProperty("审核人的名字")
	private String verifyName;
    /**
     * 审核时间
     */
	@ApiModelProperty("审核时间")
	private LocalDateTime verifyTime;
    /**
     * 审核状态，对共享到平台，团队进行审核0审核未通过，1通过，2待审核，
     */
	@ApiModelProperty("审核状态，对共享到平台，团队进行审核0审核未通过，1通过，2待审核，")
	private Integer verifyStatus;
    /**
     * 方案的编号 FA-20191010001
     */
	@ApiModelProperty("方案的编号 FA-20191010001")
	private String solutionNumber;
    /**
     * 表单当前的拥有者id
     */
	@ApiModelProperty("表单当前的拥有者id")
	private Integer autherId;
    /**
     * 当前拥有者的名字
     */
	@ApiModelProperty("当前拥有者的名字")
	private String autherName;
    /**
     * 表单当前的拥有者id
     */
	@ApiModelProperty("表单当前的拥有者id")
	private Integer currentOwnerId;
    /**
     * 当前拥有者的名字
     */
	@ApiModelProperty("当前拥有者的名字")
	private String currentOwnerName;
    /**
     * 随访次数
     */
	@ApiModelProperty("随访次数")
	private Integer visitNum;
    /**
     * 预计天数
     */
	@ApiModelProperty("预计天数")
	private Integer predictDay;
    /**
     * 版本
     */
	@ApiModelProperty("版本")
	private Integer version;
    /**
     * 创建者id
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建者id")
	private Integer createId;
    /**
     * 创建者名称
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建者名称")
	private String createName;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime;
    /**
     * 更新时间
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新时间")
	private LocalDateTime updateTime;
    /**
     * 更新时间
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新时间")
	private String updateName;
    /**
     * 更新人id
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人id")
	private Integer updateId;
    /**
     * 逻辑删除
     */
	@ApiModelProperty("逻辑删除")
    @TableLogic
	private Integer isDeleted;


}
