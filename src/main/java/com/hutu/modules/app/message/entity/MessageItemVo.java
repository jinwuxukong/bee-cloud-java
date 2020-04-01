package com.hutu.modules.app.message.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.common.entity.Patient;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息子表
 * </p>
 *
 * @author generator
 * @since 2019-10-21
 */
@Data
@Accessors(chain = true)
@TableName("t_followup_message_item")
public class MessageItemVo implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * message的id
     */
	private Integer messageId;
    /**
     * 第一类型
     */
	private Integer type;
	/**
	 * 第二类型为第一类型之下的类型1随访计划开始2随访计划结案
	 */
	private Integer parameterType;
    /**
     * 详情使用的id或参数
     */
	private String parameter;
    /**
     * 标题
     */
	private String title;
    /**
     * 内容
     */
	private String content;
    /**
     * 是否已读
     */
	private Integer isRead;
    /**
     * 接收者
     */
	private Integer toUserId;
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
    @TableLogic
	private Integer isDeleted;
	/**
	 * 是否同意
	 */
	private Integer isAgree;
	/**
	 * 患者对象
	 */
    private PatientVo patient;
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
	 * 头像
	 */
	@ApiModelProperty("头像")
	private String avatar;

}
