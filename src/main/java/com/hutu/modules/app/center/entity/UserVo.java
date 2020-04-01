package com.hutu.modules.app.center.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.hutu.modules.common.entity.Doctor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author generator
 * @since 2019-08-20
 */
@Data
@Accessors(chain = true)
@TableName("t_upms_user")
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
//    /**
//     * 所在部门
//     */
//    private String departmentId;
//    /**
//     * 部门名字
//     */
//    private String departmentName;

    @TableField(exist = false)
    private Integer[] roles;
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
     * 逻辑删除标记(已删除：1，未删除：0)
     */
    @TableLogic
    private Integer isDeleted;
    /**
     * 医院名称
     */
    private String orgName;
    /**
     * 组织Id
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer orgId;
    /**
     * 医生详情
     */
    private Doctor doctor;
    /**
     * 是否有团队
     */
    private Integer isTeam;
}
