package com.hutu.modules.app.team.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.app.followup.service.AppPlanNodeItemService;
import com.hutu.modules.app.followup.service.AppPlanNodeService;
import com.hutu.modules.app.followup.service.AppPlanService;
import com.hutu.modules.app.team.entity.TeamPatientVo;
import com.hutu.modules.app.team.mapper.TeamPatientMapper;
import com.hutu.modules.app.team.service.TeamPatientService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.team.service.TeamService;
import com.hutu.modules.common.entity.Plan;
import com.hutu.modules.common.entity.PlanNode;
import com.hutu.modules.common.entity.PlanNodeItem;
import com.hutu.modules.common.entity.TeamPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生团队患者关系中间表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-12-25
 */
@Service
public class TeamPatientServiceImpl extends ServiceImpl<TeamPatientMapper, TeamPatient> implements TeamPatientService {

    @Autowired
    private TeamPatientMapper TeamPatientMapper;
    @Autowired
    private AppPlanService appPlanService;
    @Autowired
    private AppPlanNodeService appPlanNodeService;
    @Autowired
    private AppPlanNodeItemService appPlanNodeItemService;

    @Override
    public List<TeamPatient> getTeamPatient(TeamPatient teamPatient) {
        return TeamPatientMapper.getTeamPatient(teamPatient);
    }

    @Override
    public IPage<TeamPatientVo> getPage(Page objectPage, Map<String, Object> params) {
        return TeamPatientMapper.getPage(objectPage,params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R changeDoctor(Integer patientId, Integer doctorId, String doctorName, Integer teamId) {
        //查询团队患者
        TeamPatient teamPatient = this.getOne(new QueryWrapper<TeamPatient>().eq("teamId", teamId).eq("patientId", patientId));
        Integer doctorOldId = teamPatient.getDoctorUserId();
        teamPatient.setDoctorUserId(doctorId);
        teamPatient.setDoctorName(doctorName);
        //删除以前医生的关联
        this.removeById(teamPatient.getId());
        teamPatient.setId(null);
        //新增新医生的关联
        this.save(teamPatient);
        appPlanService.changeDoctor(patientId,doctorOldId,doctorId,doctorName,teamId);
        return true?R.ok():R.error();
    }
}
