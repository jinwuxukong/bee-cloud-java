package com.hutu.modules.app.followup.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 方案节点子表
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Data
@Accessors(chain = true)
@TableName("t_followup_solution_node_item")
public class SolutionNodeItemFrom implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty("主键")
    private Integer id;
    /**
     * 方案表id
     */
    @ApiModelProperty("方案表id")
    private Integer solutionNodeId;
    /**
     * 节点名字
     */
    @ApiModelProperty("节点名字")
    private String name;
    /**
     * 模板的id
     */
    @ApiModelProperty("模板的id")
    private Integer templateId;
    /**
     * 模板的名字
     */
    @ApiModelProperty("模板的名字")
    private String templateName;
    /**
     * 模板的类型 1，量表，2 宣教
     */
    @ApiModelProperty("模板的类型 1，量表，2 宣教")
    private Integer templateType;
    /**
     * 模板类型
     */
    private Integer type;
    /**
     * 模板类型
     */
    private String typeShow;
    /**
     * 表单的类型
     */
    private Integer formType;
    /**
     * 表单类型显示
     */
    private String formTypeShow;
    /**
     * 患教类型
     */
    private Integer patientEducationType;
    /**
     * 患教类型展示
     */
    private String patientEducationTypeShow;
    /**
     * 表单内容
     */
    private String htmlContent;
    /**
     * 创建人名称
     */
    @TableField(fill = FieldFill.INSERT)
    private String createName;
    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新人名称
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateName;
    /**
     * 更新人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateId;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 逻辑删除标记(已删除：1，未删除：0)
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 状态（未启用：0，启用1，作废2）
     */
    private Integer status;

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
     * 表单的类型
     */
    private Integer scope;
    /**
     * 表单类型显示
     */
    private String scopeShow;

    /**
     * 模板的编号
     */
    private String templateNumber;

    /**
     * 是否是分数表单分析表
     */
    private Integer isScore;
    /**
     * 分析结果
     */
    private String analysisResult;
    /**
     * 查询多个id 1,2,3
     */
    private String ids;
}
