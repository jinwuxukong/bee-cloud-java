package com.hutu.modules.im.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 聊天记录表
 * </p>
 *
 * @author generator
 * @since 2019-11-27
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("聊天记录表")
@TableName("t_im_talk_room")
public class TalkRoom implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * 组员id集合，逗号隔开
     */
	@ApiModelProperty("组员id集合，逗号隔开")
	private String groupUserIds;
    /**
     * 管理员id
     */
	@ApiModelProperty("管理员id")
	private Integer managerId;
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
