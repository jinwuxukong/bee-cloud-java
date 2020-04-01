package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 全国知名医院信息表
 * </p>
 *
 * @author generator
 * @since 2019-12-05
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("全国知名医院信息表")
@TableName("t_followup_hospital")
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@ApiModelProperty("id")
	private Integer id;
    /**
     * 医院姓名
     */
	@ApiModelProperty("医院姓名")
	private String name;
    /**
     * 省份(直辖市)
     */
	@ApiModelProperty("省份(直辖市)")
	private String province;
    /**
     * 城市
     */
	@ApiModelProperty("城市")
	private String city;
    /**
     * 地址
     */
	@ApiModelProperty("地址")
	private String address;
    /**
     * 级别
     */
	@ApiModelProperty("级别")
	private String type;
    /**
     * 名字首字母拼音
     */
	@ApiModelProperty("名字首字母拼音")
	private String inputCode;


}
