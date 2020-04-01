package com.hutu.modules.admin.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.modules.common.entity.FeedbackReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 意见反馈回复表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2020-03-02
 */
@Mapper
public interface FeedbackReplyMapper extends BaseMapper<FeedbackReply> {

    List<FeedbackReply> getList(@Param("date") FeedbackReply date);

    Integer getFeedbackYear();
}