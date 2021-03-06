package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * 意见反馈表
 * </p>
 *
 * @author generator
 * @since 2020-03-02
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("意见反馈表")
@TableName("t_followup_feedback")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty("")
	private Integer id;
    /**
     * 头像
     */
	@ApiModelProperty("头像")
	private String avatar;
    /**
     * 来源（1.患者端，2医生端）
     */
	@ApiModelProperty("来源（1.患者端，2医生端）")
	private Integer source;
    /**
     * 来源id
     */
	@ApiModelProperty("来源id")
	private Integer sourceId;
    /**
     * 来源名称
     */
	@ApiModelProperty("来源名称")
	private String sourceName;
	/**
	 * 图片地址
	 */
	@ApiModelProperty("图片地址")
	private String photo;
    /**
     * 内容
     */
	@ApiModelProperty("内容")
	private String content;
    /**
     * 消息图片地址
     */
	@ApiModelProperty("消息图片地址")
	private String accessoryUrl;
    /**
     * 状态（0.待处理，1.已处理）
     */
	@ApiModelProperty("状态（0.待处理，1.已处理）")
	private Integer status;
    /**
     * 创建人ID
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人ID")
	private Integer createId;
    /**
     * 创建人名称
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人名称")
	private String createName;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime;
    /**
     * 更新人ID
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人ID")
	private Integer updateId;
    /**
     * 更新人名称
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人名称")
	private String updateName;
    /**
     * 更新时间
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新时间")
	private LocalDateTime updateTime;
    /**
     * 逻辑删除
     */
	@ApiModelProperty("逻辑删除")
    @TableLogic
	private Integer isDeleted;


}
