package com.hutu.modules.app.followup.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * app方案表
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Data
@Accessors(chain = true)
@TableName("t_followup_solution")
public class SolutionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 方案名称
     */
    private String name;
    /**
     * 专业
     */
    private Integer type;
    /**
     * 专业类型
     */
    private String typeShow;
    /**
     * 共享范围（个人0，团队1，平台2）
     */
    private Integer scope;

    /**
     * 共享范围（个人0，团队1，平台2）
     */
    private String scopeShow;
    /**
     * 介绍
     */
    private String introduce;
    /**
     * 状态（禁用0，启用1）
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

    /**
     * 当前拥有者的id
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer currentOwnerId;
    /**
     * 当前拥有者的名字
     */
    @TableField(fill = FieldFill.INSERT)
    private String currentOwnerName;
    /**
     * 拥有的时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime currentOwnerTime;

    /**
     * 审核人的id
     */
    private Integer verifyId;
    /**
     * 审核人的名字
     */
    private String verifyName;
    /**
     * 审核时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime verifyTime;
    /**
     * 审核状态，对共享到平台，团队进行审核
     */
    private Integer verifyStatus;

    /**
     * 随访次数
     */
    private Integer visitNum;

    /**
     * 预计天数
     */
    private Integer predictDay;

    /**
     * 方案节点
     */
    private List<SolutionNodeVo> SolutionNodeVoLists;
}
