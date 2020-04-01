package com.hutu.modules.admin.followup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.admin.followup.vo.AdminTeamVo;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.common.entity.Team;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 医生团队表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
public interface AdminTeamService extends IService<Team> {

    IPage<AdminTeamVo> DoctorTeamPage(Page Page, Map<String, Object> params);
}
