package com.hutu.modules.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.common.entity.TeamMember;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生团队成员表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-12-26
 */
public interface TeamMemberService extends IService<TeamMember> {

    List<TeamMember> getList(TeamMember date);

    IPage<TeamMemberVo> getPage(Page objectPage, Map<String, Object> params);

    boolean addOrUpdate(TeamMember data);

    boolean teamMembers(TeamMember data);

    TeamMemberVo getOne(TeamMember data);
}
