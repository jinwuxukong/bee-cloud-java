package com.hutu.modules.app.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.modules.common.entity.TeamMessageItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 团队消息详情表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2020-02-17
 */
@Mapper
public interface TeamMessageItemMapper extends BaseMapper<TeamMessageItem> {

    List<TeamMessageItem> getList(@Param("date") TeamMessageItem date);
}