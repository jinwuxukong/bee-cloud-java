package com.hutu.modules.admin.followup.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class DoctorVo {
    private Integer id;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 昵称姓名,本系统真实姓名
     */
    private String nick;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 认证状态
     */
    private Integer verifyStatus;
    /**
     * 职称类型
     */
    private Integer professionCallType;
    /**
     * 职称显示名
     */
    private String professionCallShow;
    /**
     * 名医设置1.名医
     */
    @ApiModelProperty("名医设置1.名医")
    private Integer famousDoctor;
    /**
     * 所在医院名称
     */
    @ApiModelProperty("所在医院名称")
    private String doctorHospital;
}
