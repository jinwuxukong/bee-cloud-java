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
 * 团队消息表
 * </p>
 *
 * @author generator
 * @since 2020-02-17
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("团队消息表")
@TableName("t_followup_team_message")
public class TeamMessage implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * 消息类型
     */
	@ApiModelProperty("消息类型")
	private Integer parameterType;
    /**
     * 团队表id
     */
	@ApiModelProperty("团队表id")
	private Integer teamId;
    /**
     * 接收者id
     */
	@ApiModelProperty("接收者id")
	private Integer toUserId;
    /**
     * 标题
     */
	@ApiModelProperty("标题")
	private String title;
    /**
     * 内容
     */
	@ApiModelProperty("内容")
	private String content;
    /**
     * 副标题
     */
	@ApiModelProperty("副标题")
	private String subtitle;
    /**
     * 状态数量（大于0为有未读，等于0为没有未读）
     */
	@ApiModelProperty("状态数量（大于0为有未读，等于0为没有未读）")
	private Integer statusNum;
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
	@ApiModelProperty("")
    @TableLogic
	private Integer isDeleted;


}
