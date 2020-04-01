package com.hutu.modules.app.followup.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 随访计划表
 * </p>
 *
 * @author generator
 * @since 2019-09-28
 */
@Data
@Accessors(chain = true)
@TableName("t_followup_plan")
public class PlanVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty("")
    private Integer id;
    /**
     * 计划名称
     */
    @ApiModelProperty("计划名称")
    private String name;
    /**
     * 患者表id
     */
    @ApiModelProperty("患者表id")
    private Integer patientId;
    /**
     * 患者姓名
     */
    @ApiModelProperty("患者姓名")
    private String patientName;
    /**
     * 关联方案id
     */
    @ApiModelProperty("关联方案id")
    private Integer solutionId;
    /**
     * 方案名称
     */
    @ApiModelProperty("方案名称")
    private String solutionName;
    /**
     * 所在团队表id
     */
    @ApiModelProperty("所在团队表id")
    private Integer teamId;
    /**
     * 随访人Id（用户表id）
     */
    @ApiModelProperty("随访人Id（用户表id）")
    private Integer executorId;
    /**
     * 随访人名称（用户表id）
     */
    @ApiModelProperty("随访人名称（用户表id）")
    private String executorName;
    /**
     * 随访进程
     */
    @ApiModelProperty("随访进程")
    private Integer visitCourse;
    /**
     * 随访次数
     */
    @ApiModelProperty("随访次数")
    private Integer visitNum;
    /**
     * 计划状态，0未开始，1进行中，2完成，3终止计划
     */
    @ApiModelProperty("计划状态，0未开始，1进行中，2完成，3终止计划")
    private Integer status;
    /**
     * 共享范围（个人0，团队1，平台2）
     */
    @ApiModelProperty("共享范围（个人0，团队1，平台2）")
    private Integer scope;
    /**
     * 共享范围（个人0，团队1，平台2）
     */
    @ApiModelProperty("共享范围（个人0，团队1，平台2）")
    private String scopeShow;
    /**
     * 方案介绍
     */
    @ApiModelProperty("方案介绍")
    private String introduce;
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private LocalDate startTime;
    /**
     * 截至时间
     */
    @ApiModelProperty("截至时间")
    private LocalDate endTime;
    /**
     * 预计天数
     */
    @ApiModelProperty("预计天数")
    private Integer predictDay;
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
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private String updateName;
    /**
     * 更新人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新人ID")
    private Integer updateId;
    @ApiModelProperty("")
    @TableLogic
    private Integer isDeleted;
    /**
     * 计划节点list
     */
    private List<PlanNodeVo> PlanNodeVo;
    /**
     * 患者id数组
     */
    private Integer[] ids;
    /**
     * 总天数
     */
    private Integer dayNum;
}
