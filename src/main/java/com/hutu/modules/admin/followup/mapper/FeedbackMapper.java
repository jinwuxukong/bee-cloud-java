package com.hutu.modules.admin.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.common.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 意见反馈表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2020-03-02
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

    List<Feedback> getList(@Param("date") Feedback date);

    Integer getFeedbackYear();

    IPage<Feedback> getPage(Page objectPage,@Param("params") Map<String, Object> params);
}