package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 随访计划节点表
 * </p>
 *
 * @author generator
 * @since 2019-12-30
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("随访计划节点表")
@TableName("t_followup_plan_node")
public class PlanNode implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * 计划表id
     */
	@ApiModelProperty("计划表id")
	private Integer followupPlanId;
    /**
     * 节点名字
     */
	@ApiModelProperty("节点名字")
	private String name;
    /**
     * 间隔数值
     */
	@ApiModelProperty("间隔数值")
	private Integer intervalDay;
    /**
     * 单位
     */
	@ApiModelProperty("单位")
	private Integer unit;
    /**
     * 单位
     */
	@ApiModelProperty("单位")
	private String unitShow;
    /**
     * 具体执行时间
     */
	@ApiModelProperty("具体执行时间")
	private LocalDate executeDate;
    /**
     * 节点排序
     */
	@ApiModelProperty("节点排序")
	private Integer orders;
    /**
     * 状态（未完成0，已完成1，未按时完成2,已终止3）
     */
	@ApiModelProperty("状态（未完成0，已完成1，未按时完成2,已终止3）")
	private Integer status;


}
