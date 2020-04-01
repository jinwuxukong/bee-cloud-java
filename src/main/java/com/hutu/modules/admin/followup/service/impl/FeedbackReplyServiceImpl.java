package com.hutu.modules.admin.followup.service.impl;

import com.hutu.modules.admin.followup.mapper.FeedbackReplyMapper;
import com.hutu.modules.admin.followup.service.FeedbackReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.common.entity.FeedbackReply;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 意见反馈回复表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2020-03-02
 */
@Service
public class FeedbackReplyServiceImpl extends ServiceImpl<FeedbackReplyMapper, FeedbackReply> implements FeedbackReplyService {

    @Autowired
    private FeedbackReplyMapper mapper;

    @Override
    public List<FeedbackReply> getList(FeedbackReply data) {
        return mapper.getList(data);
    }

    @Override
    public Integer getFeedbackYear() {
        return mapper.getFeedbackYear();
    }
}
