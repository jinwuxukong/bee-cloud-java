package com.hutu.modules.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class MyInfo extends Doctor {
    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String pass;
    /**
     * 昵称姓名,本系统真实姓名
     */
    private String nick;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户类型（0管理员，1专家，2会员医生）
     */
    private Integer type;
    /**
     * 用户类型（0管理员，1专家，2会员医生）
     */
    private String typeShow;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别(男：0，女：1)
     */
    private Integer sex;
    /**
     * 性别展示
     */
    private String sexShow;
    /**
     * 用户状态（1=正常，0=禁用）
     */
    private Integer status;
    /**
     * 状态展示
     */
    private String statusShow;
    /**
     * 排序
     */
    private Integer orders;
    /**
     * 生日
     */
    private LocalDate birthday;
}
