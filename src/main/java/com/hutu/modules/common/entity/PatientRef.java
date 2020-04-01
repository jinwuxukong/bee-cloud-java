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
 * 医生患者亲友信息表
 * </p>
 *
 * @author generator
 * @since 2019-12-27
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("医生患者亲友信息表")
@TableName("t_followup_patient_ref")
public class PatientRef implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("主键")
	private Integer id;
    /**
     * 姓名
     */
	@ApiModelProperty("姓名")
	private String name;
    /**
     * 电话
     */
	@ApiModelProperty("电话")
	private String phone;
    /**
     * 医生患者信息表id
     */
	@ApiModelProperty("医生患者信息表id")
	private Integer patientId;
    /**
     * 与患者关系
     */
	@ApiModelProperty("与患者关系")
	private String relation;


}
