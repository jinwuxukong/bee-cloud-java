package com.hutu.modules.im.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author generator
 * @since 2019-11-27
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("")
@TableName("t_im_user_login")
public class ImUserLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "userId", type = IdType.INPUT)
    @ApiModelProperty("用户id")
    private Integer userId;
    /**
     * 类型（医生：1，患者：2）
     */
    @ApiModelProperty("类型（医生：1，患者：2）")
    private Integer type;
    @ApiModelProperty("sessionId")
    private String sessionId;
	/**
	 * 状态(0 不在线，1 在线)
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
