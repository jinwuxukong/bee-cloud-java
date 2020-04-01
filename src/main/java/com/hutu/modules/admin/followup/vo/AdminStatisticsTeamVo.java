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
public class AdminStatisticsTeamVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 待审核团队
     */
    @ApiModelProperty("待审核团队")
    private Integer checkPendingTeam;

    /**
     * 院内团队
     */
    @ApiModelProperty("院内团队")
    private Integer hospitalTeam;

    /**
     * 名医团队
     */
    @ApiModelProperty("名医团队")
    private Integer MedicalTeam;

}
