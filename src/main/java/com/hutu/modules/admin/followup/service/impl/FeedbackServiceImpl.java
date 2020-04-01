package com.hutu.modules.admin.followup.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.admin.followup.mapper.FeedbackMapper;
import com.hutu.modules.admin.followup.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.common.entity.Feedback;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 意见反馈表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2020-03-02
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Autowired
    private FeedbackMapper mapper;

    @Override
    public List<Feedback> getList(Feedback data) {
        return mapper.getList(data);
    }

    @Override
    public Integer getFeedbackYear() {
        return mapper.getFeedbackYear();
    }

    @Override
    public IPage<Feedback> getPage(Page objectPage, Map<String, Object> params) {
        return mapper.getPage(objectPage,params);
    }
}
