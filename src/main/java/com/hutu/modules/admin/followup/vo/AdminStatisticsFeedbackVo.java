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
public class AdminStatisticsFeedbackVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 待处理意见个数
     */
    @ApiModelProperty("待处理意见个数")
    private Integer getFeedbackNum;

    /**
     * 本周新增意见和反馈
     */
    @ApiModelProperty("本周新增意见和反馈")
    private Integer getFeedbackWeek;

    /**
     * 本年新增意见和反馈
     */
    @ApiModelProperty("本年新增意见和反馈")
    private Integer getFeedbackYear;

}
