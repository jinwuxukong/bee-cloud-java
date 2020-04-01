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
 * 团队消息详情表
 * </p>
 *
 * @author generator
 * @since 2020-02-17
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("团队消息详情表")
@TableName("t_followup_team_message_item")
public class TeamMessageItem implements Serializable {

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
     * 0 通知类：无操作无交互
1 跳转类：可跳转查看详情
2 交互类：目前只有同意与拒绝
     */
	@ApiModelProperty("0 通知类：无操作无交互 1 跳转类：可跳转查看详情 2 交互类：目前只有同意与拒绝")
	private Integer itemType;
    /**
     * 处理状态，0未处理，1同意，2拒绝
     */
	@ApiModelProperty("处理状态，0未处理，1同意，2拒绝")
	private Integer status;
    /**
     * 参数类型：第二类型0系统升级1随访开始2随访结束3随访申请4.已收到量表5.患者取消关注6.团队新成员7.团队成员退出8.团队你有新的患者9.团队申请加入10.团队删除成员11.团队公告12.团队新增患者
     */
	@ApiModelProperty("参数类型：第二类型0系统升级1随访开始2随访结束3随访申请4.已收到量表5.患者取消关注6.团队新成员7.团队成员退出8.团队你有新的患者9.团队申请加入10.团队删除成员11.团队公告12.团队新增患者")
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
