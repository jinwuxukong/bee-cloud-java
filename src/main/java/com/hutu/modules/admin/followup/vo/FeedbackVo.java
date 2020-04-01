package com.hutu.modules.admin.followup.vo;

import com.hutu.modules.common.entity.Feedback;
import com.hutu.modules.common.entity.FeedbackReply;
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
public class FeedbackVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 意见反馈
     */
    @ApiModelProperty("意见反馈")
    private Feedback feedback;

    /**
     * 意见反馈回复
     */
    @ApiModelProperty("意见反馈回复")
    private FeedbackReply feedbackReply;

}
