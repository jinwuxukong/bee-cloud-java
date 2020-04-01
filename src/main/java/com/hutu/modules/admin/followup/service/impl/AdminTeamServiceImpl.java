package com.hutu.modules.admin.followup.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.admin.followup.mapper.AdminTeamMemberMapper;
import com.hutu.modules.admin.followup.vo.AdminTeamVo;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.common.entity.Team;
import com.hutu.modules.admin.followup.mapper.AdminTeamMapper;
import com.hutu.modules.admin.followup.service.AdminTeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 医生团队表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Service
public class AdminTeamServiceImpl extends ServiceImpl<AdminTeamMapper, Team> implements AdminTeamService {

    @Autowired
    private AdminTeamMemberMapper adminTeamMemberMapper;

    @Override
    public IPage<AdminTeamVo> DoctorTeamPage(Page Page, Map<String, Object> params) {
        return adminTeamMemberMapper.DoctorTeamPage(Page,params);
    }
}
