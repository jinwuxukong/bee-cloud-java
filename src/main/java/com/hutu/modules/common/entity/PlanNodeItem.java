package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 随访计划节点子表
 * </p>
 *
 * @author generator
 * @since 2019-12-30
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel("随访计划节点子表")
@TableName("t_followup_plan_node_item")
public class PlanNodeItem implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * 随访计划节点表id
     */
	@ApiModelProperty("随访计划节点表id")
	private Integer nodeId;
    /**
     * 0 待完成，1 已完成，2，超时，3已终止
     */
	@ApiModelProperty("0 待完成，1 已完成，2，超时，3已终止")
	private Integer status;
    /**
     * 节点名字
     */
	@ApiModelProperty("节点名字")
	private String name;
    /**
     * 医生id(用户表主键)
     */
	@ApiModelProperty("医生id(用户表主键)")
	private Integer doctorId;
    /**
     * 所在团队表id
     */
	@ApiModelProperty("所在团队表id")
	private Integer teamId;
    /**
     * 随访类型（0计划随访，1临时随访）
     */
	@ApiModelProperty("随访类型（0计划随访，1临时随访）")
	private Integer scaleType;
    /**
     * 随访类型（0计划随访，1临时随访）
     */
	@ApiModelProperty("随访类型（0计划随访，1临时随访）")
	private String scaleTypeShow;
    /**
     * 患者id
     */
	@ApiModelProperty("患者id")
	private Integer patientId;
    /**
     * 患者姓名
     */
	@ApiModelProperty("患者姓名")
	private String patientName;
    /**
     * 患者头像
     */
	@ApiModelProperty("患者头像")
	private String patientPhoto;
    /**
     * 患者手机号
     */
	@ApiModelProperty("患者手机号")
	private String phone;
    /**
     * 通知状态0.未通知1.已通知
     */
	@ApiModelProperty("通知状态0.未通知1.已通知")
	private Integer informState;
    /**
     * 通知状态显示
     */
	@ApiModelProperty("通知状态显示")
	private String informStateShow;
    /**
     * 表单id
     */
	@ApiModelProperty("表单id")
	private Integer templateId;
    /**
     * 量表答案
     */
	@ApiModelProperty("量表答案")
	private String answer;
    /**
     * 是否发送微信0.没发送1.已发送
     */
	@ApiModelProperty("是否发送微信0.没发送1.已发送")
	private Integer isSendWeChat;
    /**
     * 是否微信已读0.未读1已读
     */
	@ApiModelProperty("是否微信已读0.未读1已读")
	private Integer isWeChatRead;
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
	@ApiModelProperty("")
    @TableLogic
	private Integer isDeleted;


}
