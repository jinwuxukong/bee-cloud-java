package com.hutu.modules.admin.followup.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 统计
 * </p>
 *
 * @author generator
 * @since 2019-09-28
 */
@Data
@Accessors(chain = true)
public class AdminStatisticsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 待认证医生
     */
    @ApiModelProperty("待认证医生")
    private int certifiedPhysician;

    /**
     * 名医/医生数量
     */
    @ApiModelProperty("名医/医生数量")
    private String famousDoctorNum;

    /**
     * 注销医生
     */
    @ApiModelProperty("注销医生")
    private int logoutDoctor;

}
