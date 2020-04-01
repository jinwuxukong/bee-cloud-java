package com.hutu.modules.app.followup.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.hutu.modules.common.entity.PatientCases;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 患者病历信息
 * </p>
 *
 * @author generator
 * @since 2019-12-27
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("患者病历信息")
@TableName("t_followup_patient_cases")
public class PatientCasesVo implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
	/**
	 * 名字
	 */
	@ApiModelProperty("名字")
	private String name;
	/**
	 * 性别
	 */
	@ApiModelProperty("性别")
	private Integer sex;
	/**
	 * 性别
	 */
	@ApiModelProperty("性别")
	private String sexShow;
	/**
	 * 头像
	 */
	@ApiModelProperty("头像")
	private String avatar;
	/**
	 * 年龄
	 */
	@ApiModelProperty("年龄")
	private Integer age;
	/**
	 * 病例集合
	 */
	private List<PatientCases> patientCasesList;


}
