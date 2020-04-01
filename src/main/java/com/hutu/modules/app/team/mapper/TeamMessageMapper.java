package com.hutu.modules.app.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.modules.common.entity.TeamMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 团队消息表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2020-02-17
 */
@Mapper
public interface TeamMessageMapper extends BaseMapper<TeamMessage> {

    List<TeamMessage> getList(@Param("date") TeamMessage date);

    Integer isMessage(@Param("params") Map<String, Object> params);
}