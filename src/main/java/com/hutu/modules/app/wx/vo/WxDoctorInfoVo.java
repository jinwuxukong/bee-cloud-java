package com.hutu.modules.app.wx.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Accessors(chain = true)
public class WxDoctorInfoVo implements Serializable {

    /**
     * 用户表id
     */
    private Integer id;
    /**
     * 医生名称
     */
    private String doctorName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别(男：0，女：1)
     */
    private Integer sex;
    /**
     * 性别展示
     */
    private String sexShow;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 依米id
     */
    private String yimiNumber;
    /**
     * 团队id
     */
    private Integer doctorTeamId;
    /**
     * 团队名称
     */
    private String doctorTeam;
    /**
     * 所在医院名称
     */
    private String doctorHospital;
    /**
     * 职称类型
     */
    private Integer professionCallType;
    /**
     * 职称显示名
     */
    private String professionCallShow;
    /**
     * 职称证地址
     */
    private String jobUrl;
    /**
     * 认证状态
     */
    private Integer verifyStatus;

    /**
     * 认证人的id
     */
    private Integer verifyId;
    /**
     * 认证人的名字
     */
    private String verifyName;
    /**
     * 医生职业地址
     */
    private String professionUrl;
    /**
     * 医生资格证
     */
    private String authUrl1;
    /**
     * 穿白大褂的照片
     */
    private String authUrl2;
    /**
     * 身份证的正面
     */
    private String idCardUrl1;
    /**
     * 身份证反面
     */
    private String idCardUrl2;
    /**
     * 部门
     */
    private String department;
    /**
     * 我的简介
     */
    private String introduction;
    /**
     * 擅长手术，多个逗号隔开
     */
    private String operation;
    /**
     * 疾病编码
     */
    private String icd10;
    /**
     * 擅长疾病，多个逗号隔开
     */
    private String disease;
    /**
     * 荣誉证书地址
     */
    private String honorCertificates;

}
