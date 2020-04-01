package com.hutu.modules.app.wx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.modules.admin.upms.service.UserService;
import com.hutu.modules.app.team.service.TeamMemberService;
import com.hutu.modules.app.team.service.TeamPatientService;
import com.hutu.modules.app.team.service.TeamService;
import com.hutu.modules.app.wx.service.WxDoctorService;
import com.hutu.modules.app.wx.service.WxPatientCasesService;
import com.hutu.modules.app.wx.service.WxPatientService;
import com.hutu.modules.common.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 微信患者-我的医生模块
 *
 * @author hutu
 * @date 2019-12-27 18:54
 */
@Api(tags = "微信患者-我的医生模块")
@RestController
@RequestMapping("wxMyDoctor")
public class WxMyDoctorController {

    @Autowired
    private WxPatientService patientService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private TeamPatientService teamPatientService;

    @Autowired
    private WxDoctorService wxDoctorService;

    @Autowired
    private WxPatientCasesService patientCasesService;

    @AuthIgnore
    @ApiOperation("通过患者id获取默认关注团队信息")
    @GetMapping("/getDefaultTeamInfo/{patientId}")
    public R getDefaultTeamInfo(@ApiParam("患者id") @PathVariable("patientId") String patientId) {
        Patient patient = patientService.getById(patientId);
        if (patient == null) {
            return R.error("查无此患者, id为： " + patientId);
        }
        if (patient.getDefaultTeamId() == null) {
            return R.ok().put("info", null);
        }
        Team team = teamService.getById(patient.getDefaultTeamId());
        return R.ok().put("info", team);
    }

    @AuthIgnore
    @ApiOperation("通过id获取团队信息")
    @GetMapping("/getTeamInfo/{id}")
    public R getTeamInfo(@ApiParam("团队id") @PathVariable("id") String id) {
        return R.ok().put("info", teamService.getById(id));
    }

    @AuthIgnore
    @ApiOperation("设置团队为默认团队")
    @GetMapping("/setDefaultTeam")
    public R getTeamInfo(@ApiParam("团队id")String teamId,@ApiParam("患者id")String patientId) {
        return patientService.update(new UpdateWrapper<Patient>().set("defaultTeamId", teamId).eq("id", patientId))?R.ok():R.error("设置失败");
    }

    @AuthIgnore
    @ApiOperation("修改对团队的关注状态")
    @GetMapping("/updateFollowStatus")
    public R updateFollowStatus(@ApiParam("团队id") Integer teamId, @ApiParam("患者id") Integer patientId, @ApiParam("关注状态（0取关，1关注）") Integer status) {

        boolean update = teamPatientService.update(new UpdateWrapper<TeamPatient>()
                .set(status != null, "isBinding", status)
                .eq("teamId", teamId).eq("patientId", patientId));

        Patient patient = patientService.getById(patientId);
        // 若取消的是默认团队
        if (patient.getDefaultTeamId() != null && teamId.equals(patient.getDefaultTeamId()) && Objects.equals(status, 0)) {
            patient.setDefaultTeamId(null);
            patientService.update(new UpdateWrapper<Patient>().set("defaultTeamId", null).eq("id", patient.getId()));
        }

        // 更新失败说明是新增的关系，此处新建一条
        if (!update){
            Team team = teamService.getById(teamId);
            teamPatientService.save(new TeamPatient().setTeamId(teamId).setPatientId(patientId).setPatientName(patient.getName())
                    .setDoctorName(team.getOwnerName())
                    .setDoctorUserId(team.getOwnerId())
                    .setIsBinding(status));
        }
        return R.ok();
    }

    @AuthIgnore
    @ApiOperation("通过患者id获取关注团队列表信息")
    @GetMapping("/getTeamList/{patientId}")
    public R getTeamList(@ApiParam("患者id") @PathVariable("patientId") String patientId) {

        List<TeamPatient> list = teamPatientService.list(new QueryWrapper<TeamPatient>().select("teamId").eq("patientId", patientId).eq("isBinding",1));
        if (list==null||list.size()==0){
            return R.ok().put("list", null);
        }

        Set<Integer> teamIds = list.stream().map(TeamPatient::getTeamId).collect(Collectors.toSet());
        Collection<Team> teams = teamService.listByIds(teamIds);

        Patient patient = patientService.getById(patientId);

        Integer defaultTeamId = patient.getDefaultTeamId();
        ArrayList<Team> resultList = new ArrayList<>();
        Team defaultTeam = null;

        for (Team obj : teams) {
            if (obj.getId().equals(defaultTeamId)) {
                defaultTeam = obj;
            } else {
                resultList.add(obj);
            }
        }

        return R.ok().put("list", resultList).put("defaultTeam",defaultTeam);
    }

    @AuthIgnore
    @ApiOperation("通过患者id获取推荐关注团队列表信息")
    @GetMapping("/getMatchTeamList/{patientId}")
    public R getMatchTeamList(@ApiParam("患者id") @PathVariable("patientId") String patientId) {

        List<PatientCases> patientCases = patientCasesService.list(new QueryWrapper<PatientCases>().eq("patientId", patientId));

        // 获取病历中的医院，然后去匹配医疗团队
        ArrayList<String> hospitalNames = new ArrayList<>();
        patientCases.forEach(obj -> hospitalNames.add(obj.getHospitalName()));

        if (hospitalNames.size() == 0) {
            return R.ok().put("list",new ArrayList<Team>());
        }
        // 所有团队
        List<Team> teams = teamService.list(new QueryWrapper<Team>().in("hospital", hospitalNames));
        // 患者已关注的团队
        List<TeamPatient> teamPatients = teamPatientService.list(new QueryWrapper<TeamPatient>().eq("patientId", patientId).eq("isBinding",1));

        ArrayList<Integer> teamIds = new ArrayList<>();
        teamPatients.forEach(obj -> teamIds.add(obj.getTeamId()));
        // 排除已经关注的团队
        teams.removeIf(obj -> teamIds.contains(obj.getId()));
        return R.ok().put("list",teams);
    }

    @AuthIgnore
    @ApiOperation("通过团队id获取队员列表")
    @GetMapping("/getDoctors/{teamId}")
    public R getDoctors(@ApiParam("团队id") @PathVariable("teamId") String teamId) {
        // 按拥有者->管理员->普通程序排序
        List<TeamMember> teamMembers = teamMemberService.list(new QueryWrapper<TeamMember>().eq("teamId", teamId).eq("verifyStatus",1).orderByDesc("type"));
        return R.ok().put("list", teamMembers);
    }

    @AuthIgnore
    @ApiOperation("通过医生id获取医生信息(id为userId)")
    @GetMapping("/getDoctorInfo/{id}")
    public R getDoctorInfo(@ApiParam("医生id") @PathVariable("id") Integer id) {
        return R.ok().put("info", wxDoctorService.getDoctorInfoByUserId(id));
    }

}
