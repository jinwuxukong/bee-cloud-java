package com.hutu.modules.admin.followup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.common.entity.FeedbackReply;

import java.util.List;
/**
 * <p>
 * 意见反馈回复表 服务类
 * </p>
 *
 * @author generator
 * @since 2020-03-02
 */
public interface FeedbackReplyService extends IService<FeedbackReply> {

    List<FeedbackReply> getList(FeedbackReply date);

    Integer getFeedbackYear();
}
