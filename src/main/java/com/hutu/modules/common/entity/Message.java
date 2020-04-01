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
 * 系统消息表
 * </p>
 *
 * @author generator
 * @since 2020-01-10
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("系统消息表")
@TableName("t_followup_message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * 类型0系统1随访
     */
	@ApiModelProperty("类型0系统1随访")
	private Integer type;
    /**
     * 第二类型0系统升级1随访开始2随访结束3随访申请4.已收到量表5.患者取消关注
     */
	@ApiModelProperty("第二类型0系统升级1随访开始2随访结束3随访申请4.已收到量表5.患者取消关注")
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
