package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * icd10字典表
 * </p>
 *
 * @author generator
 * @since 2019-12-05
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("icd10字典表")
@TableName("t_followup_icd10")
public class Icd10 implements Serializable {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty("")
	private Integer id;
	@ApiModelProperty("")
	private String icd9;
	@ApiModelProperty("")
	private String icd10;
    /**
     * 疾病名称
     */
	@ApiModelProperty("疾病名称")
	private String name;
    /**
     * 拼音输入简写
     */
	@ApiModelProperty("拼音输入简写")
	private String inputCode;
    /**
     * 其他输入法简写
     */
	@ApiModelProperty("其他输入法简写")
	private String inputCodeExt;


}
