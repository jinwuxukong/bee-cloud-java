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
public class AdminTeamMemberStatisticsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 新申请医生
     */
    @ApiModelProperty("新申请医生")
    private Integer newApplication;

    /**
     * 名医/医生数量
     */
    @ApiModelProperty("名医/医生数量")
    private String famousDoctorNum;

    /**
     * 注销医生
     */
    @ApiModelProperty("注销医生")
    private Integer logoutDoctor;

}
