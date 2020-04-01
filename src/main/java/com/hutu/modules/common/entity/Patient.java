package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 医生患者信息表
 * </p>
 *
 * @author generator
 * @since 2019-12-27
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("医生患者信息表")
@TableName("t_followup_patient")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty("")
	private Integer id;
    /**
     * 名字
     */
	@ApiModelProperty("名字")
	private String name;
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
	 * 头像
	 */
	@ApiModelProperty("头像")
	private String avatar;
    /**
     * 生日
     */
	@ApiModelProperty("生日")
	private LocalDate birthday;
    /**
     * 电话
     */
	@ApiModelProperty("电话")
	private String phone;
    /**
     * 身高
     */
	@ApiModelProperty("身高")
	private Integer height;
    /**
     * 体重
     */
	@ApiModelProperty("体重")
	private Integer weight;
    /**
     * 体重
     */
	@ApiModelProperty("bim")
	private String bim;
    /**
     * 腰围
     */
	@ApiModelProperty("腰围")
	private Integer waistline;
    /**
     * 吸烟史（0无，1有）
     */
	@ApiModelProperty("吸烟史（0无，1有）")
	private String smoker;
    /**
     * 吸烟史（0无，1有）
     */
	@ApiModelProperty("吸烟史（0无，1有）")
	private String drinker;
    /**
     * 肝功能（0正常，1异常）
     */
	@ApiModelProperty("肝功能（0正常，1异常）")
	private String liver;
    /**
     * 肾功能（0正常，1异常）
     */
	@ApiModelProperty("肾功能（0正常，1异常）")
	private String kidney;
    /**
     * 婚姻状况（0未婚，1已婚，2其他）
     */
	@ApiModelProperty("婚姻状况（0未婚，1已婚，2其他）")
	private String marryStatus;
    /**
     * 生育状态（0未生育，1备孕期，2怀孕期，已生育）
     */
	@ApiModelProperty("生育状态（0未生育，1备孕期，2怀孕期，3已生育）")
	private String fertilityState;
    /**
     * 慢性病，多个逗号隔开
     */
	@ApiModelProperty("慢性病，多个逗号隔开")
	private String chronicDisease;
    /**
     * 家族史，多个逗号隔开
     */
	@ApiModelProperty("家族史，多个逗号隔开")
	private String familyDisease;
    /**
     * 药物过敏，多个逗号隔开
     */
	@ApiModelProperty("药物过敏，多个逗号隔开")
	private String drugAllergy;
    /**
     * 食物或接触物过敏，多个逗号隔开
     */
	@ApiModelProperty("食物或接触物过敏，多个逗号隔开")
	private String foodAllergy;
    /**多个逗号隔开
     * 个人习惯，
     */
	@ApiModelProperty("个人习惯，多个逗号隔开")
	private String habit;
    /**
     * 身份证
     */
	@ApiModelProperty("身份证")
	private String idCard;
    /**
     * 患者标签，多个用逗号隔开
     */
	@ApiModelProperty("患者标签，多个用逗号隔开")
	private String label;
    /**
     * 病历附件地址，多个用逗号隔开
     */
	@ApiModelProperty("病历附件地址，多个用逗号隔开")
	private String medicalUrl;
    /**
     * 患者的openId
     */
	@ApiModelProperty("患者的openId")
	private String openId;
    /**
     * 医保卡号
     */
	@ApiModelProperty("医保卡号")
	private String healthCareCardNumber;
    /**
     * 就诊卡号
     */
	@ApiModelProperty("就诊卡号")
	private String medicalCardNumber;
    /**
     * 是否关注0未关注，1已关注
     */
	@ApiModelProperty("是否关注0未关注，1已关注")
	private Integer isAttention;

	@ApiModelProperty("默认关注医生团队id")
	private Integer defaultTeamId;
    /**
     * 创建人ID
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人ID")
	private Integer createId;
    /**
     * 创建人名称
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人名称")
	private String createName;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime;
    /**
     * 更新人ID
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人ID")
	private Integer updateId;
    /**
     * 更新人名称
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人名称")
	private String updateName;
    /**
     * 更新时间
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新时间")
	private LocalDateTime updateTime;
    /**
     * 逻辑删除
     */
	@ApiModelProperty("逻辑删除")
    @TableLogic
	private Integer isDeleted;


}
