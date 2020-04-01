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
 * 个人消息表
 * </p>
 *
 * @author generator
 * @since 2020-01-10
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("个人消息表")
@TableName("t_followup_message_item")
public class MessageItem implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * message的id
     */
	@ApiModelProperty("message的id")
	private Integer messageId;
    /**
     * 类型0系统1随访2团队
     */
	@ApiModelProperty("类型0系统1随访2团队")
	private Integer type;
	/**
	 * 0 通知类：无操作无交互\r\n1 跳转类：可跳转查看详情\r\n2 交互类：目前只有同意与拒绝
	 */
	@ApiModelProperty("0 通知类：无操作无交互\\r\\n1 跳转类：可跳转查看详情\\r\\n2 交互类：目前只有同意与拒绝")
	private Integer itemType;
    /**
     * 处理状态，0未处理，1同意，2拒绝
     */
	@ApiModelProperty("处理状态，0未处理，1同意，2拒绝")
	private Integer status;
    /**
     * 参数类型
     */
	@ApiModelProperty("参数类型")
	private Integer parameterType;
    /**
     * 消息参数
     */
	@ApiModelProperty("消息参数")
	private String parameter;
    /**
     * 标题
     */
	@ApiModelProperty("标题")
	private String title;
    /**
     * 头像
     */
	@ApiModelProperty("头像")
	private String avatar;
    /**
     * 内容
     */
	@ApiModelProperty("内容")
	private String content;
    /**
     * 是否已读
     */
	@ApiModelProperty("是否已读")
	private Integer isRead;
    /**
     * 接收者
     */
	@ApiModelProperty("接收者")
	private Integer toUserId;
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
