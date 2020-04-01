package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
public class PatientCases implements Serializable {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty("")
	private Integer id;
    /**
     * 患者id
     */
	@ApiModelProperty("患者id")
	private Integer patientId;
    /**
     * 就诊时间
     */
	@ApiModelProperty("就诊时间")
	private LocalDate visitTime;
    /**
     * 就诊类型（0手术，1初诊，2复诊，3体检）
     */
	@ApiModelProperty("就诊类型（0手术，1初诊，2复诊，3体检）")
	private Integer visitType;
    /**
     * 医院名称
     */
	@ApiModelProperty("医院名称")
	private String hospitalName;
    /**
     * 科室名称
     */
	@ApiModelProperty("科室名称")
	private String departmentName;
    /**
     * 住院号
     */
	@ApiModelProperty("住院号")
	private Integer admissionNumber;
    /**
     * 就诊卡号
     */
	@ApiModelProperty("就诊卡号")
	private String visitCard;
    /**
     * 手术名称
     */
	@ApiModelProperty("手术名称")
	private String operationName;
    /**
     * 疾病icd10编码
     */
	@ApiModelProperty("疾病icd10编码")
	private String diseaseIcd10;
    /**
     * 手术日期
     */
	@ApiModelProperty("手术日期")
	private LocalDate operationDate;
    /**
     * 备注
     */
	@ApiModelProperty("备注")
	private String remark;
    /**
     * 附件图片地址用，隔开
     */
	@ApiModelProperty("附件图片地址用，隔开")
	private String accessoryUrl;
    /**
     * 创建人ID
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人ID")
	private Integer createId;
    /**
     * 创建人名称
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人名称")
	private String createName;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime;
    /**
     * 更新人ID
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人ID")
	private Integer updateId;
    /**
     * 更新人名称
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人名称")
	private String updateName;
    /**
     * 更新时间
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新时间")
	private LocalDateTime updateTime;
    /**
     * 逻辑删除
     */
	@ApiModelProperty("逻辑删除")
    @TableLogic
	private Integer isDeleted;


}
