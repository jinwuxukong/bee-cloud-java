package com.hutu.modules.app.followup.entity;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 医生患者信息表
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */
@Data
@Accessors(chain = true)
public class PatientVo implements Serializable {

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
    /**
     * 方案名称
     */
    @ApiModelProperty("方案名称")
    private String solutionName;
    /**
     * 计划名称
     */
    @ApiModelProperty("计划名称")
    private String planName;
    /**
     * 绑定状态
     */
    @ApiModelProperty("绑定状态")
    private Integer isBinding;
    /**
     * 是否有计划
     */
    @ApiModelProperty("是否有计划")
    private Integer isPlan;
    /**
     * 医生id
     */
    @ApiModelProperty("医生id")
    private Integer doctorUserId;
    /**
     * 医生名称
     */
    @ApiModelProperty("医生名称")
    private String doctorName;
    /**
     * 团队患者中间表id
     */
    @ApiModelProperty("团队患者中间表id")
    private Integer teamPatientId;
    /**
     * 团队名称
     */
    @ApiModelProperty("团队名称")
    private String TeamName;
    /**
     * 团队id
     */
    @ApiModelProperty("团队id")
    private String TeamId;
}
