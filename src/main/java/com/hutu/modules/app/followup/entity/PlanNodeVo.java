package com.hutu.modules.app.followup.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 随访计划节点表
 * </p>
 *
 * @author generator
 * @since 2019-09-28
 */
@Data
@Accessors(chain = true)
@TableName("t_followup_plan_node")
public class PlanNodeVo implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 随访表id
     */
	private Integer followupPlanId;
    /**
     * 节点名字
     */
	private String name;
    /**
     * 间隔数值
     */
	private Integer intervalDay;
    /**
     * 单位
     */
	private Integer unit;
    /**
     * 单位
     */
	private String unitShow;
    /**
     * 具体执行时间
     */
	private LocalDate executeDate;
    /**
     * 状态（未完成0，已完成1）
     */
	private Integer status;
	/**
	 * 表单计划节点子节点
	 */
	private List<PlanNodeItemVo> FormPlanNodeItemList;
	/**
	 * 宣教计划节点子节点
	 */
	private List<PlanNodeItemVo> VisitPlanNodeItemList;


}
