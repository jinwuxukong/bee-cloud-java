package com.hutu.modules.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.team.entity.TeamVo;
import com.hutu.modules.common.entity.Team;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生团队表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-12-26
 */
public interface TeamService extends IService<Team> {

    List<Team> getList(Team date);

    boolean createOrUpdate(Team data);

    IPage<TeamVo> applyPage(Page objectPage, Map<String, Object> params);

    IPage<TeamVo> getPage(Page objectPage, Map<String, Object> params);

    boolean isRead(Integer teamId, Integer parameterType);
}
