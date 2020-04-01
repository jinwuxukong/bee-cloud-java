package com.hutu.modules.app.team.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.constant.Constant;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.center.entity.UserVo;
import com.hutu.modules.app.center.service.AppDoctorService;
import com.hutu.modules.app.center.service.MyCenterService;
import com.hutu.modules.app.message.service.MessageItemService;
import com.hutu.modules.app.message.service.MessageService;
import com.hutu.modules.app.team.entity.TeamVo;
import com.hutu.modules.app.team.mapper.TeamMapper;
import com.hutu.modules.app.team.service.TeamMemberService;
import com.hutu.modules.app.team.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.common.entity.Doctor;
import com.hutu.modules.common.entity.Team;
import com.hutu.modules.common.entity.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生团队表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-12-26
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private TeamMemberService teamMemberService;
    @Autowired
    private MyCenterService myCenterService;
    @Autowired
    private AppDoctorService appDoctorService;
    @Autowired
    private MessageItemService messageItemService;

    @Override
    public List<Team> getList(Team data) {
        return teamMapper.getList(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createOrUpdate(Team data) {
        if(data.getId()!=null){
            return this.saveOrUpdate(data);
        }else{
            UserVo showInfo = myCenterService.getShowInfo(JwtUtils.getUserId());
            data.setOwnerId(showInfo.getId());
            data.setOwnerName(showInfo.getNick());
            data.setApplyCount(0);
            boolean b = this.saveOrUpdate(data);
            TeamMember teamMember = new TeamMember();
            teamMember.setTeamId(data.getId());
            teamMember.setUserId(showInfo.getId());
            teamMember.setName(showInfo.getNick());
            teamMember.setDepartment(data.getDepartment());
            teamMember.setJobType(showInfo.getDoctor().getProfessionCallShow());
            teamMember.setType(3);
            teamMember.setTypeShow("拥有者");
            teamMember.setVerifyStatus(1);
            boolean save = teamMemberService.save(teamMember);
            boolean update = appDoctorService.update(new UpdateWrapper<Doctor>().set("doctorTeamId", data.getId()).set("doctorTeam", data.getName()).eq("userId", showInfo.getId()));
            if(b && save && update){
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public IPage<TeamVo> applyPage(Page objectPage, Map<String, Object> params) {
        return teamMapper.applyPage(objectPage,params);
    }

    @Override
    public IPage<TeamVo> getPage(Page objectPage, Map<String, Object> params) {
        return teamMapper.getPage(objectPage,params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean isRead(Integer teamId, Integer parameterType) {
        boolean update = this.update(new UpdateWrapper<Team>().set("applyCount", 0).eq("id", teamId));
        boolean b = messageItemService.readUpdateByIds(Constant.MESSAGE_GROUP, parameterType,teamId);
        return update&&b?true:false;
    }
}
