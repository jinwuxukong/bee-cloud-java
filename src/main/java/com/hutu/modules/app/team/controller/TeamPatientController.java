package com.hutu.modules.app.team.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.modules.app.common.dictionaries.service.HospitalDepartmentService;
import com.hutu.modules.app.followup.service.PatientCasesService;
import com.hutu.modules.app.team.entity.TeamPatientVo;
import com.hutu.modules.app.team.service.TeamPatientService;
import com.hutu.modules.common.entity.HospitalDepartment;
import com.hutu.modules.common.entity.PatientCases;
import com.hutu.modules.common.entity.TeamPatient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 医生团队患者关系中间表 前端控制器
 *
 * @author generator
 * @since 2019-12-25
 */

@Api(tags = "医生团队患者关系中间表")
@RestController
@RequestMapping("teamPatient")
public class TeamPatientController{
    @Autowired
    private TeamPatientService teamPatientService;
    @Autowired
    private PatientCasesService patientCasesService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("姓名") @RequestParam(required = false) String name,
                     @ApiParam("团队id") @RequestParam(required = false) String teamId,
                     @ApiParam("医生id") @RequestParam(required = false) String doctorUserId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(name)){
            params.put("name",name);
        }
        if(teamId!=null){
            params.put("teamId",teamId);
        }
        if(doctorUserId!=null){
            params.put("doctorUserId",doctorUserId);
        }
        IPage<TeamPatientVo> page = teamPatientService.getPage(new Page<>(current,pageSize),params);
        int teamId1 = teamPatientService.count(new QueryWrapper<TeamPatient>().eq("teamId", teamId));
        for (TeamPatientVo record : page.getRecords()) {
            record.setCount(teamId1);
        }
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")TeamPatient data){
        return teamPatientService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return teamPatientService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",teamPatientService.getById(id));
    }
    @AuthIgnore
    @PostMapping("/getTeamPatient")
    public R getTeamPatient(@ApiParam("数据对象")TeamPatient teamPatient){
        return R.ok().put("info",teamPatientService.getTeamPatient(teamPatient));
    }

    @ApiOperation("通过患者id获取病例相关科室")
    @GetMapping("/getDepartment/{patientId}")
    public R getDepartment(@ApiParam("患者id")@PathVariable("patientId")String patientId){
        List<PatientCases> patientCasesList = patientCasesService.list(new QueryWrapper<PatientCases>().eq("patientId", patientId));
        List<String> department = new ArrayList();
        patientCasesList.forEach(obj -> department.add(obj.getDepartmentName()));
        return R.ok().put("info",department.stream().distinct().collect(Collectors.toList()));
    }

    @ApiOperation("患者更换医生")
    @GetMapping("/changeDoctor/{patientId}/{doctorId}/{teamId}/{doctorName}")
    public R changeDoctor(@ApiParam("患者id")@PathVariable("patientId")Integer patientId,
                          @ApiParam("医生id")@PathVariable("doctorId")Integer doctorId,
                          @ApiParam("医生名称")@PathVariable("doctorName")String doctorName,
                          @ApiParam("团队id")@PathVariable("teamId")Integer teamId){
        return teamPatientService.changeDoctor(patientId,doctorId, doctorName,teamId);
    }
}
