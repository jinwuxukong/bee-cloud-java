package com.hutu.modules.admin.upms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 组织
 * </p>
 *
 * @author generator
 * @since 2019-08-16
 */
@Data
@Accessors(chain = true)
@TableName("t_upms_organization")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 所属上级
     */
	private Integer pId;
    /**
     * 组织名称
     */
	private String name;
    /**
     * 类型(门店：1，供应商: 2)
     */
	private Integer type;
    /**
     * 类型值
     */
	private String typeShow;
    /**
     * 组织描述
     */
	private String description;
    /**
     * 排序
     */
	private Integer orders;
    /**
     * 联系人
     */
	private String contacts;
    /**
     * 联系人手机号
     */
	private String contactsPhone;
    /**
     * 区域
     */
	private String area;
    /**
     * 详细地址
     */
	private String address;
    /**
     * 管理员集合id
     */
	private String adminIds;
    /**
     * 单位logo
     */
	private String logo;
    /**
     * 法人
     */
	private String leader;
    /**
     * 固定电话
     */
	private String fixedTelephone;
    /**
     * 注册地址
     */
	private String registerAddress;
    /**
     * 传真
     */
	private String fax;
    /**
     * 邮箱
     */
	private String mail;
    /**
     * 经验范围
     */
	private String scope;
    /**
     * 资质证件类型(证件类型：多证合一1，传统三证2)
     */
	private String certificateType;
    /**
     * 资质证件Id集合，多个逗号隔开
     */
	private String certificateIds;
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


}
