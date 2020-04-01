package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 方案节点子表
 * </p>
 *
 * @author generator
 * @since 2019-12-27
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("方案节点子表")
@TableName("t_followup_solution_node_item")
public class SolutionNodeItem implements Serializable {

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


}
