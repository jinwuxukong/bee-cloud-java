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
 * 医生信息表
 * </p>
 *
 * @author generator
 * @since 2020-04-01
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("医生信息表")
@TableName("t_followup_doctor")
public class Doctor implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * 用户表id
     */
	@ApiModelProperty("用户表id")
	private Integer userId;
    /**
     * 依米id
     */
	@ApiModelProperty("依米id")
	private String yimiNumber;
    /**
     * 默认团队首页显示的团队id
     */
	@ApiModelProperty("默认团队首页显示的团队id")
	private Integer doctorTeamId;
    /**
     * 默认团队首页显示的团队
     */
	@ApiModelProperty("默认团队首页显示的团队")
	private String doctorTeam;
    /**
     * 医生执业地址
     */
	@ApiModelProperty("医生执业地址")
	private String professionUrl;
    /**
     * 所在医院名称
     */
	@ApiModelProperty("所在医院名称")
	private String doctorHospital;
    /**
     * 所在医院级别
     */
	@ApiModelProperty("所在医院级别")
	private String doctorHospitalLevel;
    /**
     * 职称类型
     */
	@ApiModelProperty("职称类型")
	private Integer professionCallType;
    /**
     * 职称显示名
     */
	@ApiModelProperty("职称显示名")
	private String professionCallShow;
    /**
     * 名医设置1.名医
     */
	@ApiModelProperty("名医设置1.名医")
	private Integer famousDoctor;
    /**
     * 拒绝理由
     */
	@ApiModelProperty("拒绝理由")
	private String reason;
    /**
     * 是否通过认证，0未通过，1通过，2待审核，3注销.4.未审核状态
     */
	@ApiModelProperty("是否通过认证，0未通过，1通过，2待审核，3注销.4.未审核状态")
	private Integer verifyStatus;
    /**
     * 审核人id
     */
	@ApiModelProperty("审核人id")
	private Integer verifyId;
    /**
     * 审核人名字
     */
	@ApiModelProperty("审核人名字")
	private String verifyName;
    /**
     * 审核次数
     */
	@ApiModelProperty("审核次数")
	private Integer verifyNum;
    /**
     * 医师资格证地址
     */
	@ApiModelProperty("医师资格证地址")
	private String authUrl1;
    /**
     * 穿白大褂图片地址
     */
	@ApiModelProperty("穿白大褂图片地址")
	private String authUrl2;
    /**
     * 身份证正面
     */
	@ApiModelProperty("身份证正面")
	private String idCardUrl1;
    /**
     * 身份证反面
     */
	@ApiModelProperty("身份证反面")
	private String idCardUrl2;
    /**
     * 职称证地址
     */
	@ApiModelProperty("职称证地址")
	private String jobUrl;
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
     * 手机号
     */
	@ApiModelProperty("手机号")
	private String phone;
    /**
     * 部门
     */
	@ApiModelProperty("部门")
	private String department;
    /**
     * 我的简介
     */
	@ApiModelProperty("我的简介")
	private String introduction;
    /**
     * 擅长手术，多个逗号隔开
     */
	@ApiModelProperty("擅长手术，多个逗号隔开")
	private String operation;
    /**
     * 疾病号码
     */
	@ApiModelProperty("疾病号码")
	private String icd10;
    /**
     * 擅长疾病，多个逗号隔开
     */
	@ApiModelProperty("擅长疾病，多个逗号隔开")
	private String disease;
    /**
     * 荣誉证书附件的ids，用 , 分割开
     */
	@ApiModelProperty("荣誉证书附件的ids，用 , 分割开")
	private String honorCertificates;


}
