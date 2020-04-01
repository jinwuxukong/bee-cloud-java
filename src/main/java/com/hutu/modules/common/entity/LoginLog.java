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
 * 登陆日志表
 * </p>
 *
 * @author generator
 * @since 2020-03-17
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("登陆日志表")
@TableName("t_followup_login_log")
public class LoginLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 登陆地点
	 */
	@ApiModelProperty("登陆地点")
	private String landingLocation;
	/**
	 * ip地址
	 */
	@ApiModelProperty("ip地址")
	private String ipAddress;
	/**
	 * 登陆时长
	 */
	@ApiModelProperty("登陆时长")
	private Double landingTime;
	/**
	 * 类型0正常登陆，1异常登陆
	 */
	@ApiModelProperty("类型0正常登陆，1异常登陆")
	private Integer type;
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
