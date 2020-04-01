package com.hutu.modules.admin.followup.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.admin.followup.service.AdminDoctorService;
import com.hutu.modules.admin.followup.service.AdminTeamMemberService;
import com.hutu.modules.admin.followup.vo.AdminTeamMemberStatisticsVo;
import com.hutu.modules.app.center.entity.UserVo;
import com.hutu.modules.app.center.service.MyCenterService;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.app.team.service.TeamService;
import com.hutu.modules.common.entity.Doctor;
import com.hutu.modules.common.entity.Feedback;
import com.hutu.modules.common.entity.TeamMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医生团队成员表 前端控制器
 *
 * @author generator
 * @since 2019-12-26
 */

@Api(tags = "随访运营端-医生团队成员表")
@RestController
@RequestMapping("AdminTeamMember")
public class AdminTeamMemberController {
    @Autowired
    private AdminTeamMemberService adminTeamMemberService;
    @Autowired
    private MyCenterService myCenterService;
    @Autowired
    private AdminDoctorService adminDoctorService;

    /**
     * 团队医生成员列表
     * @param current
     * @param pageSize
     * @param teamId
     * @return
     */
    @ApiOperation("团队医生成员列表")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("名称") @RequestParam(required = false) String keyWord,
                     @ApiParam("团队id") @RequestParam(required = false) Integer teamId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(teamId!=null){
            params.put("teamId",teamId);
        }
        if(StrUtil.isNotEmpty(keyWord)){
            params.put("name",keyWord);
        }
        if(StrUtil.isNotEmpty(keyWord)){
            params.put("phone",keyWord);
        }
        if(StrUtil.isNotEmpty(keyWord)){
            params.put("hospital",keyWord);
        }
        IPage<TeamMemberVo> page = adminTeamMemberService.getPage(new Page<>(current,pageSize),params);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")TeamMember data){
        return adminTeamMemberService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",adminTeamMemberService.getById(id));
    }
    @ApiOperation("通过对象获取列表数据")
    @PostMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") TeamMember data){
        return R.ok().put("info",adminTeamMemberService.getList(data));
    }
    @ApiOperation("团队医生详情")
    @PostMapping("/getOne")
    public R getOne(@RequestBody @ApiParam("数据对象") TeamMember data){
        TeamMemberVo TeamMemberVo = adminTeamMemberService.getOne(data);
        UserVo showInfo = myCenterService.getShowInfo(TeamMemberVo.getUserId());
        TeamMemberVo.setUserVo(showInfo);
        return R.ok().put("info",TeamMemberVo);
    }

    @ApiOperation("统计")
    @GetMapping("/statistics/{teamId}")
    public R statistics(@ApiParam("团队id")@PathVariable("teamId")Integer teamId){
        AdminTeamMemberStatisticsVo adminTeamMemberStatisticsVo = new AdminTeamMemberStatisticsVo();
        adminTeamMemberStatisticsVo.setNewApplication(adminTeamMemberService.count(new QueryWrapper<TeamMember>().eq("teamId",teamId).eq("verifyStatus",0)));
        List<TeamMember> teamMemberLists = adminTeamMemberService.list(new QueryWrapper<TeamMember>().eq("teamId", teamId).eq("verifyStatus", 1));
        List userIds = new ArrayList();
        teamMemberLists.forEach(obj->userIds.add(obj.getUserId()));
        adminTeamMemberStatisticsVo.setFamousDoctorNum(adminDoctorService.count(new QueryWrapper<Doctor>().in("userId",userIds).eq("famousDoctor",1))+"/"+adminTeamMemberService.count(new QueryWrapper<TeamMember>().eq("verifyStatus",1).eq("teamId",teamId)));
        adminTeamMemberStatisticsVo.setLogoutDoctor(0);
        return R.ok().put("info",adminTeamMemberStatisticsVo);
    }

    @ApiOperation("新申请医生")
    @GetMapping("/newApplication/{teamId}")
    public R certifiedPhysician(@ApiParam("团队id")@PathVariable("teamId")Integer teamId) {
        return R.ok().put("info",adminTeamMemberService.count(new QueryWrapper<TeamMember>().eq("teamId",teamId).eq("verifyStatus",0)));
    }

    @ApiOperation("名医/医生数量")
    @GetMapping("/famousDoctorNum/{teamId}")
    public R famousDoctorNum(@ApiParam("团队id")@PathVariable("teamId")Integer teamId) {
        List<TeamMember> teamMemberLists = adminTeamMemberService.list(new QueryWrapper<TeamMember>().eq("teamId", teamId).eq("verifyStatus", 1));
        int[] userIds = new int[teamMemberLists.size()];
        for (int i = 0; i < teamMemberLists.size(); i++) {
            userIds[i]=teamMemberLists.get(i).getUserId();
        }
        return R.ok().put("info",adminDoctorService.count(new QueryWrapper<Doctor>().in("userId",userIds).eq("famousDoctor",1))+"/"+adminTeamMemberService.count(new QueryWrapper<TeamMember>().eq("verifyStatus",1).eq("teamId",teamId)));
    }

    @ApiOperation("注销医生")
    @GetMapping("/logoutDoctor")
    public R logoutDoctor() {
        return R.ok().put("info",0);
    }
}
