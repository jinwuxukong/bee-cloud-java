package com.hutu.modules.admin.followup.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hutu.modules.common.entity.SolutionNodeItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 方案节点表
 * </p>
 *
 * @author generator
 * @since 2019-12-27
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("方案节点表")
public class SolutionNodeFrom implements Serializable {

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
     * 逻辑删除
     */
	@ApiModelProperty("逻辑删除")
    @TableLogic
	private Integer isDeleted;
	/**
	 * 表单集合
	 */
	@ApiModelProperty("表单集合")
	private List<SolutionNodeItem> FormSolutionNodeItemList;
	/**
	 * 宣教集合
	 */
	@ApiModelProperty("宣教集合")
	private List<SolutionNodeItem> VisitSolutionNodeItemList;


}
