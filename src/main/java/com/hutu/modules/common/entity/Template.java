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
 * 量表单模板表
 * </p>
 *
 * @author generator
 * @since 2020-03-16
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("量表单模板表")
@TableName("t_form_template")
public class Template implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
	/**
	 * 模板名称
	 */
	@ApiModelProperty("模板名称")
	@TableField(value = "`name`")
	private String name;
	/**
	 * 模板的编号
	 */
	@ApiModelProperty("模板的编号")
	private String templateNumber;
	/**
	 * 模板类型
	 */
	@ApiModelProperty("模板类型")
	private Integer type;
	/**
	 * 类型显示，1问题表单，2宣教内容
	 */
	@ApiModelProperty("类型显示，1问题表单，2宣教内容")
	private String typeShow;
	/**
	 * 表单的类型，字典表中(按科室分类)
	 */
	@ApiModelProperty("表单的类型，字典表中(按科室分类)")
	private Integer formType;
	/**
	 * 表单类型
	 */
	@ApiModelProperty("表单类型")
	private String formTypeShow;
	/**
	 * 手术名称
	 */
	@ApiModelProperty("手术名称")
	private String operationName;
	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	@TableField(value = "`desc`")
	private String desc;
	/**
	 * 开始语
	 */
	@ApiModelProperty("开始语")
	private String startWord;
	/**
	 * 结束语
	 */
	@ApiModelProperty("结束语")
	private String endWord;
	/**
	 * 状态（未启用：0，启用1）
	 */
	@ApiModelProperty("状态（未启用：0，启用1）")
	@TableField(value = "`status`")
	private Integer status;
	/**
	 * 表单内容
	 */
	@ApiModelProperty("表单内容")
	private String htmlContent;
	/**
	 * 表单作者id
	 */
	@ApiModelProperty("表单作者id")
	private Integer autherId;
	/**
	 * 表单作者的名字
	 */
	@ApiModelProperty("表单作者的名字")
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
	 * 审核人的id
	 */
	@ApiModelProperty("审核人的id")
	private Integer verifyId;
	/**
	 * 审核人的名称
	 */
	@ApiModelProperty("审核人的名称")
	private String verifyName;
	/**
	 * 审核时间
	 */
	@ApiModelProperty("审核时间")
	private LocalDateTime verifyTime;
	/**
	 * 审核状态，对共享到平台，团队进行审核
	 */
	@ApiModelProperty("审核状态，对共享到平台，团队进行审核")
	private Integer verifyStatus;
	/**
	 * 0 个人，1 团队 2平台
	 */
	@ApiModelProperty("0 个人，1 团队 2平台")
	private Integer scope;
	/**
	 * 0 个人，1 团队 2平台
	 */
	@ApiModelProperty("0 个人，1 团队 2平台")
	private String scopeShow;
	/**
	 * 是否是分数表单分析表
	 */
	@ApiModelProperty("是否是分数表单分析表")
	private Integer isScore;
	/**
	 * 结果分析规则,json串
	 */
	@ApiModelProperty("结果分析规则,json串")
	private String analysisResult;
	/**
	 * 版本
	 */
	@ApiModelProperty("版本")
	private Float version;
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
	/**
	 * 更新人名称
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人名称")
	private String updateName;
	/**
	 * 更新人ID
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人ID")
	private Integer updateId;
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新时间")
	private LocalDateTime updateTime;
	/**
	 * 逻辑删除标记(已删除：1，未删除：0)
	 */
	@ApiModelProperty("逻辑删除标记(已删除：1，未删除：0)")
	@TableLogic
	private Integer isDeleted;


}
