package com.hutu.modules.im.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author generator
 * @since 2019-09-25
 */
@Data
@Accessors(chain = true)
@TableName("t_im_talk_record")
public class TalkRecord implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 内容
     */
	private String content;
    /**
     * 类型
     */
	private String type;
    /**
     * 发送者
     */
	private Integer fromUserId;
    /**
     * 接受者
     */
	private Integer toUserId;
	/**
	 * 房间号
	 */
	private Integer roomId;
	/**
	 * 状态(0 发送失败，1 成功)
	 */
	private Integer status;
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
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateName;
	/**
	 * 更新人id
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Integer updateId;
	@TableLogic
	private Integer isDeleted;

}
