package com.hutu.modules.app.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.team.entity.TeamVo;
import com.hutu.modules.common.entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 医生团队表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-12-26
 */
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

    List<Team> getList(@Param("date") Team date);

    IPage<TeamVo> applyPage(Page objectPage, Map<String, Object> params);

    IPage<TeamVo> getPage(Page objectPage, Map<String, Object> params);
}