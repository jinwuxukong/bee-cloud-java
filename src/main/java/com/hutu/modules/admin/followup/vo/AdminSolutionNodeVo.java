package com.hutu.modules.admin.followup.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 方案节点表
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Data
@Accessors(chain = true)
@TableName("t_followup_solution_node")
public class AdminSolutionNodeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 方案表id
     */
    private Integer solutionId;
    /**
     * 节点名字
     */
    private String name;
    /**
     * 间隔数值
     */
    private Integer intervalValue;
    /**
     * 单位
     */
    private Integer unit;
    /**
     * 单位
     */
    private String unitShow;
    /**
     * 间隔多少天
     */
    private int intervalDay;
    /**
     * 节点排序
     */
    @ApiModelProperty("节点排序")
    private Integer orders;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;
    /**
     * 表单方案节点子节点
     */
    private List<AdminSolutionNodeItemVo> FormSolutionNodeItemList;
    /**
     * 宣教方案节点子节点
     */
    private List<AdminSolutionNodeItemVo> VisitSolutionNodeItemList;
}
