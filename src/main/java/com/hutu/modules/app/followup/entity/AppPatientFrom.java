package com.hutu.modules.app.followup.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.hutu.modules.common.entity.PatientRef;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 医生患者信息表
 * </p>
 *
 * @author generator
 * @since 2019-10-14
 */
@Data
@Accessors(chain = true)
@TableName("t_followup_doctor_patient")
public class AppPatientFrom implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户表id，医生
     */
    private Integer doctorUserId;
    /**
     * 名字
     */
    private String name;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 性别
     */
    private String sexShow;
    /**
     * 生日
     */
    private LocalDate birthday;
    /**
     * 婚姻状况
     */
    private Integer marryStatus;
    /**
     * 电话
     */
    private String phone;
    /**
     * 身份证
     */
    private String idCard;
    /**
     * 就诊时间
     */
    private LocalDate visitTime;
    /**
     * 就诊医院
     */
    private String visitHospital;
    /**
     * 手术时间
     */
    private LocalDate operationTime;
    /**
     * 手术名称
     */
    private String operationName;
    /**
     * 诊断
     */
    private String diagnosis;
    /**
     * 基本病情
     */
    private String baseInfo;
    /**
     * 患者标签，多个用逗号隔开
     */
    private String label;
    /**
     * 病历附件地址，多个用逗号隔开
     */
    private String medicalUrl;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDeleted;
    /**
     * 是否有计划状态0无计划1有计划
     */
    private Integer isPlan;
    /**
     * 患者的openId
     */
    private String openId;
    /**
     * 医生患者的绑定状态
     */
    private Integer bindStatus;
    /**
     * 就诊卡号
     */
    private Integer medicalCardNumber;
    /**
     * 医保卡号
     */
    private Integer healthCareCardNumber;
    /**
     * 创建人名称
     */
    @TableField(fill = FieldFill.INSERT)
    private String createName;
    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新人名称
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateName;
    /**
     * 更新人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateId;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 患者家屬
     */
    private List<PatientRef> patientRef;
    /**
     * 参数值
     */
    private String parameter;

}
