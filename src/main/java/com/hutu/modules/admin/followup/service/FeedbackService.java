package com.hutu.modules.admin.followup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.common.entity.Feedback;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 意见反馈表 服务类
 * </p>
 *
 * @author generator
 * @since 2020-03-02
 */
public interface FeedbackService extends IService<Feedback> {

    List<Feedback> getList(Feedback date);

    Integer getFeedbackYear();

    IPage<Feedback> getPage(Page objectPage, Map<String, Object> params);
}
