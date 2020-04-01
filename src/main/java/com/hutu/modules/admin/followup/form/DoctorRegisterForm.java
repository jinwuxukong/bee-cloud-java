package com.hutu.modules.admin.followup.form;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 云随访医生注册的form
 */
@Data
@Accessors(chain = true)
public class DoctorRegisterForm {
    /**
     * 用户名(手机号)
     */
    public String phone;

    /**
     * 密码
     */
    public String password;

    /**
     * 真实姓名
     */
    private String nick;

    /**
     * 医院名字
     */
    private String doctorHospital;

    /**
     * 职称
     */
    private String professionCallShow;

    /**
     * 医院科室
     */
    private String department;

    /**
     * 擅长及诊所介绍
     */
    private String introduction;
}
