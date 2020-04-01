package com.hutu.modules.app.team.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.constant.Constant;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.center.entity.UserVo;
import com.hutu.modules.app.center.service.AppDoctorService;
import com.hutu.modules.app.center.service.MyCenterService;
import com.hutu.modules.app.message.entity.MessageFrom;
import com.hutu.modules.app.message.service.MessageItemService;
import com.hutu.modules.app.message.service.MessageService;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.app.team.mapper.TeamMemberMapper;
import com.hutu.modules.app.team.service.TeamMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.team.service.TeamMessageItemService;
import com.hutu.modules.app.team.service.TeamMessageService;
import com.hutu.modules.app.team.service.TeamService;
import com.hutu.modules.common.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 医生团队成员表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-12-26
 */
@Service
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember> implements TeamMemberService {

    @Autowired
    private TeamMemberMapper mapper;
    @Autowired
    private MyCenterService myCenterService;
    @Autowired
    private AppDoctorService appDoctorService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamMessageService teamMessageService;
    @Autowired
    private TeamMessageItemService teamMessageItemService;

    @Override
    public List<TeamMember> getList(TeamMember data) {
        return mapper.getList(data);
    }

    @Override
    public IPage<TeamMemberVo> getPage(Page objectPage, Map<String, Object> params) {
        return mapper.getPage(objectPage,params);
    }

    @Override
    @Transactional( rollbackFor = Exception.class)
    public boolean addOrUpdate(TeamMember data) {
        UserVo showInfo = myCenterService.getShowInfo(JwtUtils.getUserId());
        Team Team = teamService.getById(data.getTeamId());
        if(showInfo.getDoctor().getDoctorTeamId()==null){
            appDoctorService.setDefaultTeam(data.getUserId(),data.getTeamId(),Team.getName());
        }
        data.setUserId(showInfo.getId());
        data.setName(showInfo.getNick());
        data.setDepartment(showInfo.getDoctor().getDepartment());
        data.setJobType(showInfo.getDoctor().getProfessionCallShow());
        data.setType(1);
        data.setTypeShow("普通");
        data.setVerifyStatus(0);
        boolean b = this.saveOrUpdate(data);
        boolean update = teamService.update(new UpdateWrapper<Team>().set("applyCount", Team.getApplyCount() + 1).eq("id", data.getTeamId()));
        //发送申请加入消息
        List<MessageFrom> messageFroms = new ArrayList<>();
        MessageFrom messageFrom = new MessageFrom();
        messageFrom.setParameterType(Constant.MESSAGE_TEAM_APPLY_JOIN);
        messageFrom.setItemType(Constant.MESSAGE_ITEM_TYPE_INTERACTIVE);
        messageFrom.setFirstTitle("申请加入");
        messageFrom.setContent(showInfo.getNick()+" "+showInfo.getDoctor().getProfessionCallShow()+" "+showInfo.getDoctor().getDepartment()+" "+showInfo.getDoctor().getDoctorHospital());
        messageFrom.setTitle("申请加入");
        messageFrom.setStatus(0);
        messageFrom.setParameter(data.getId().toString());
        messageFrom.setTeamId(data.getTeamId());
        messageFrom.setToUserId(JwtUtils.getUserId());
        messageFroms.add(messageFrom);
        teamMessageService.utilMessage(1,messageFroms);
        return b&&update?true:false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean teamMembers(TeamMember data) {
        TeamMember teamMember = this.getById(data.getId());
        UserVo showInfo = myCenterService.getShowInfo(teamMember.getUserId());
        Team Team = teamService.getById(teamMember.getTeamId());
        if(data.getVerifyStatus()==1){
            boolean b = this.saveOrUpdate(data);
            TeamMessage message = teamMessageService.getOne(new QueryWrapper<TeamMessage>().eq("teamId", Team.getId()).eq("parameterType", Constant.MESSAGE_TEAM_APPLY_JOIN));
            boolean update = teamMessageItemService.update(new UpdateWrapper<TeamMessageItem>().set("status", 1).eq("messageId",message.getId()).eq("parameter", data.getId()));
            List<MessageFrom> messageFroms = new ArrayList<>();
            MessageFrom messageFrom = new MessageFrom();
            messageFrom.setParameterType(Constant.MESSAGE_TEAM_NEWCOMER);
            messageFrom.setItemType(Constant.MESSAGE_ITEM_TYPE_NOTICE);
            messageFrom.setFirstTitle("团队信息");
            String age = showInfo.getBirthday() != null ? DateUtil.ageOfNow(showInfo.getBirthday().toString())+"":"";
            String sex = "";
            if(StrUtil.isNotEmpty(showInfo.getSexShow())){
                sex = showInfo.getSexShow();
            }
            messageFrom.setContent(showInfo.getNick()+" "+age+" "+sex);
            messageFrom.setTitle("新成员加入");
            messageFrom.setTeamId(Team.getId());
            messageFrom.setParameter(teamMember.getUserId().toString());
            messageFrom.setToUserId(Team.getId());
            teamMessageService.createMessage(messageFrom);
            teamMessageService.utilMessage(1,messageFroms);
            if(showInfo.getDoctor().getDoctorTeamId()==null){
                appDoctorService.update(new UpdateWrapper<Doctor>().set("doctorTeamId", Team.getId()).set("doctorTeam", Team.getName()).eq("userId", teamMember.getUserId()));
            }
            if(b&&update){
                return true;
            }else{
                return false;
            }
        }else{
            boolean b = this.saveOrUpdate(data);
            TeamMessage message = teamMessageService.getOne(new QueryWrapper<TeamMessage>().eq("teamId", Team.getId()).eq("toUserId", Team.getCreateId()).eq("parameterType", Constant.MESSAGE_TEAM_APPLY_JOIN));
            boolean update = teamMessageItemService.update(new UpdateWrapper<TeamMessageItem>().set("status", 2).eq("messageId",message.getId()).eq("parameter", data.getId()));
            if(b&&update){
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public TeamMemberVo getOne(TeamMember data) {
        return mapper.getOne(data);
    }
}
