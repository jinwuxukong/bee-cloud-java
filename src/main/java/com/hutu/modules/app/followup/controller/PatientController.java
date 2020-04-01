package com.hutu.modules.app.followup.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.followup.entity.PatientFrom;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.app.followup.service.AppPlanNodeItemService;
import com.hutu.modules.app.followup.service.AppPlanService;
import com.hutu.modules.app.followup.service.PatientService;
import com.hutu.modules.app.team.service.TeamPatientService;
import com.hutu.modules.common.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 医生患者信息表 app
 * </p>
 *
 * @author generator
 * @since 2019-10-14
 */

@Api(tags = "App医生患者信息表")
@RestController
@RequestMapping("patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private AppPlanService appPlanService;
    @Autowired
    private AppPlanNodeItemService appPlanNodeItemService;
    @Autowired
    private TeamPatientService teamPatientService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("名字")@RequestParam(required = false) String name,
                     @ApiParam("分类")@RequestParam(required = false) String label,
                     @ApiParam("计划")@RequestParam(required = false) String isPlan,
                     @ApiParam("绑定状态")@RequestParam(required = false) Integer bindStatus,
                     @ApiParam("团队id")@RequestParam(required = false) Integer TeamId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(name)){
            params.put("name",name);
        }
        if(StrUtil.isNotEmpty(label)){
            params.put("label",label);
        }
        if(StrUtil.isNotEmpty(isPlan)){
            params.put("isPlan",isPlan);
        }
        if(bindStatus!=null){
            params.put("bindStatus",bindStatus);
        }
        if(TeamId!=null){
            params.put("TeamId",TeamId);
        }
        Integer userId = JwtUtils.getUserId();
        params.put("userId",userId);
        IPage<PatientVo> page = patientService.pagePatient(new Page<>(current, pageSize),params);
        for (PatientVo record : page.getRecords()) {
            if(record.getIsPlan()==1){
                List<Plan> list = appPlanService.list(new QueryWrapper<Plan>().eq("patientId", record.getId()).orderByDesc("createTime"));
                record.setPlanName(list.get(0).getName());
            }
        }
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") PatientFrom data) {
        Integer userId = JwtUtils.getUserId();
        data.setDoctorUserId(userId);
        return patientService.createOrUpdate(data,data.getPatientRef());
    }

    @ApiOperation("我的患者统计")
    @GetMapping("/statistics")
    public R statistics() {
        return R.ok().put("info", teamPatientService.count(new QueryWrapper<TeamPatient>().eq("doctorUserId",JwtUtils.getUserId())));
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) throws Exception {
//        return patientService.Delete(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
        return null;
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") Integer id) {
        return R.ok().put("info", patientService.read(id,JwtUtils.getUserId()));
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/getOne/{id}")
    public R getOne(@ApiParam("数据对象id") @PathVariable("id") Integer id) {
        return R.ok().put("info", patientService.getById(id));
    }

    @ApiOperation("通过对象获取列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") Patient data){
        return R.ok().put("info",patientService.getList(data));
    }

}
