package com.hutu.modules.admin.upms.form;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 注册表单
 *
 * @author hutu
 * @date 2019/8/20 13:48
 */
@Data
@Accessors(chain = true)
public class ExpertRegisterForm implements Serializable {
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String pass;
	/**
	 * 昵称姓名,本系统采用为真实姓名
	 */
	private String nick;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 医院名称
	 */
	private String orgName;
	/**
	 * 医院等级
	 */
	private String orgLevel;
	/**
	 * 职称
	 */
	private String jobType;
	/**
	 * 所在部门
	 */
	private String departmentId;

	/**
	 * 部门名称
	 */
	private String departmentName;
	/**
	 * 用户类型（0管理员，1专家，2普通医生）
	 */
	private Integer type;
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
	 * 用户状态（1=正常，0=禁用）
	 */
	private Integer status;
	/**
	 * 排序
	 */
	private Integer orders;
	/**
	 * 生日
	 */
	private LocalDate birthday;

	/**
	 * 性别展示
	 */
	private String sexShow;
	/**
	 * 状态展示
	 */
	private String statusShow;
	/**
	 * 职称
	 */
	private String professionCall;

}
