package com.hutu.modules.admin.followup.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.common.constant.Constant;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.admin.followup.mapper.AdminTeamMemberMapper;
import com.hutu.modules.admin.followup.service.AdminTeamMemberService;
import com.hutu.modules.app.center.entity.UserVo;
import com.hutu.modules.app.center.service.AppDoctorService;
import com.hutu.modules.app.center.service.MyCenterService;
import com.hutu.modules.app.message.entity.MessageFrom;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.app.team.mapper.TeamMemberMapper;
import com.hutu.modules.app.team.service.TeamMemberService;
import com.hutu.modules.app.team.service.TeamMessageItemService;
import com.hutu.modules.app.team.service.TeamMessageService;
import com.hutu.modules.app.team.service.TeamService;
import com.hutu.modules.common.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生团队成员表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-12-26
 */
@Service
public class AdminTeamMemberServiceImpl extends ServiceImpl<AdminTeamMemberMapper, TeamMember> implements AdminTeamMemberService {

    @Autowired
    private AdminTeamMemberMapper mapper;

    @Override
    public List<TeamMember> getList(TeamMember data) {
        return mapper.getList(data);
    }

    @Override
    public IPage<TeamMemberVo> getPage(Page objectPage, Map<String, Object> params) {
        return mapper.getPage(objectPage,params);
    }

    @Override
    public TeamMemberVo getOne(TeamMember data) {
        return mapper.getOne(data);
    }
}
