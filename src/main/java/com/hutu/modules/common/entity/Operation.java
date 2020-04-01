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
 * 手术术式表
 * </p>
 *
 * @author generator
 * @since 2019-12-05
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("手术术式表")
@TableName("t_followup_operation")
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * 手术名
     */
	@ApiModelProperty("手术名")
	private String name;
    /**
     * 拼音输入简码
     */
	@ApiModelProperty("拼音输入简码")
	private String inputCode;
	/**
	 * 手术编码icd-9-cm3
	 */
	@ApiModelProperty("手术编码icd-9-cm3")
	private String code;
	/**
	 * 疾病名称
	 */
	@ApiModelProperty("疾病名称")
	private String diseaseName;
	/**
	 * 疾病icd10编码
	 */
	@ApiModelProperty("疾病icd10编码")
	private String diseaseIcd10;
	/**
	 * 手术类别
	 */
	@ApiModelProperty("手术类别")
	private String type;
	/**
	 * 手术级别
	 */
	@ApiModelProperty("手术级别")
	private String operationLevel;
}
