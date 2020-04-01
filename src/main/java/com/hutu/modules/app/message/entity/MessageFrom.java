package com.hutu.modules.app.message.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息传参
 * </p>
 *
 * @author generator
 * @since 2019-10-21
 */
@Data
@Accessors(chain = true)
@TableName("t_followup_message")
public class MessageFrom implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * '类型0系统1随访2团队',
     */
	private Integer type;
    /**
     * 0 通知类：无操作无交互\r\n1 跳转类：可跳转查看详情\r\n2 交互类：目前只有同意与拒绝
     */
    private Integer itemType;
    /**
     * 参数类型：第二类型0系统升级1随访开始2随访结束3随访申请4.已收到量表5.患者取消关注6.团队新成员7.团队成员退出8.团队你有新的患者9.团队申请加入10.团队删除成员11.团队公告12.团队新增患者
     */
    private Integer parameterType;
    /**
     * 团队表id
     */
    @ApiModelProperty("团队表id")
    private Integer teamId;
    /**
     * 标题
     * 标题 名字
     */
    private String title;
    /**
     * 随访申请
     *
     * 第一标题
     */
    private String firstTitle;
    /**
     * 申请与您建立随访关系
     * 副标题
     */
	private String subtitle;
    /**
     *  申请与您建立随访关系
     * 内容
     */
	private String content;
    /**
     * 状态数量（大于0为有未读，等于0为没有未读）
     */
	private Integer statusNum;
    /**
     * 详情使用的id或参数
     */
    private String parameter;
    /**
     * 接收者
     * 医生的id
     *
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
     * 是否同意状态
     */
    private Integer status;


}
