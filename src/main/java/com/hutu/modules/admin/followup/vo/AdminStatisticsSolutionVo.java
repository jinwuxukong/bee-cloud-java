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
public class AdminStatisticsSolutionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 官方方案
     */
    @ApiModelProperty("官方方案")
    private int officialSolution;

    /**
     * 待审核方案
     */
    @ApiModelProperty("待审核方案")
    private int checkPendingSolution;

    /**
     * 个人方案
     */
    @ApiModelProperty("个人方案")
    private int personageSolution;

}
