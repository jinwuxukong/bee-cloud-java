package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 方案节点表
 * </p>
 *
 * @author generator
 * @since 2020-03-27
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("方案节点表")
@TableName("t_followup_solution_node")
public class SolutionNode implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * 随访表id
     */
	@ApiModelProperty("随访表id")
	private Integer solutionId;
    /**
     * 节点名字
     */
	@ApiModelProperty("节点名字")
	private String name;
    /**
     * 间隔数值
     */
	@ApiModelProperty("间隔数值")
	private Integer intervalValue;
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
     * 间隔多少天
     */
	@ApiModelProperty("间隔多少天")
	private Integer intervalDay;
    /**
     * 节点排序
     */
	@ApiModelProperty("节点排序")
	private Integer orders;
    /**
     * 量表数量
     */
	@ApiModelProperty("量表数量")
	private Integer lbNum;
    /**
     * 宣教数量
     */
	@ApiModelProperty("宣教数量")
	private Integer xjNum;
    /**
     * 逻辑删除
     */
	@ApiModelProperty("逻辑删除")
    @TableLogic
	private Integer isDeleted;


}
