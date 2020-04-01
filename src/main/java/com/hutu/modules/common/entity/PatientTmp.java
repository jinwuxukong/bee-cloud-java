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
 * 医生表id-微信患者openId临时关联表
 * </p>
 *
 * @author generator
 * @since 2019-11-23
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("医生表id-微信患者openId临时关联表")
@TableName("t_followup_doctor_patient_tmp")
public class PatientTmp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("主键")
	private Integer id;
	@ApiModelProperty("")
	private Integer doctorId;
    /**
     * 患者的openId
     */
	@ApiModelProperty("患者的openId")
	private String openId;
    /**
     * 临时表 关联关系逻辑删除
     */
	@ApiModelProperty("临时表 关联关系逻辑删除")
    @TableLogic
	private Integer isDeleted;


}
