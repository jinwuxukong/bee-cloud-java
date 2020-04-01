package com.hutu.modules.app.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hutu.modules.common.entity.HospitalDepartment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 医院科室表
 * </p>
 *
 * @author generator
 * @since 2019-12-05
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("医院科室表")
@TableName("t_followup_hospital_department")
public class HospitalDepartmentVo implements Serializable {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty("")
	private Integer id;
    /**
     * 科室姓名
     */
	@ApiModelProperty("科室姓名")
	private String name;
    /**
     * 一级科室，二级科室
     */
	@ApiModelProperty("一级科室，二级科室")
	private Integer type;
    /**
     * 父科室Id
     */
	@ApiModelProperty("父科室Id")
	private Integer parentId;
    /**
     * 首字母拼音
     */
	@ApiModelProperty("首字母拼音")
	private String inputCode;
	/**
	 * 二级科室
	 */
	@ApiModelProperty("二级科室")
	private List<HospitalDepartment> hospitalDepartmentList;

}
