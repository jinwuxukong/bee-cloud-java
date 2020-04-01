package com.hutu.modules.app.followup.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.followup.entity.PlanVo;
import com.hutu.modules.app.followup.service.*;
import com.hutu.modules.app.message.service.MessageService;
import com.hutu.modules.common.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 随访计划表 App
 * </p>
 *
 * @author generator
 * @since 2019-10-15
 */

@Api(tags = "App随访计划表")
@RestController
@RequestMapping("AppPlan")
@Slf4j
public class AppPlanController {
    @Autowired
    private AppPlanService appPlanService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("名称") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        Page<Plan> page=new Page<>(current,pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        queryWrapper.orderByDesc("createTime");
        appPlanService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("通过患者id获取医生患者计划page")
    @GetMapping("/getPageVoList/{current}/{pageSize}")
    public R getPageVoList(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                           @ApiParam("计划名称") @RequestParam(required = false) String name,
                           @ApiParam("患者id") @RequestParam(required = false) Integer patientId,
                           @ApiParam("团队id") @RequestParam(required = false) Integer teamId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(name)){
            params.put("name",name);
        }
        if(patientId!=null){
            params.put("patientId",patientId);
        }
        if(teamId!=null){
            params.put("teamId",teamId);
        }
        params.put("userId", JwtUtils.getUserId());
        IPage<PlanVo> page = appPlanService.PageVoList(new Page<>(current,pageSize),params);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }

    @ApiOperation("通过患者id获取团队计划page")
    @GetMapping("/getTeamPlanPage/{current}/{pageSize}")
    public R getTeamPlanPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                           @ApiParam("计划名称") @RequestParam(required = false) String name,
                           @ApiParam("患者id") @RequestParam(required = false) Integer patientId,
                           @ApiParam("医生id") @RequestParam(required = false) Integer userId,
                           @ApiParam("团队id") @RequestParam(required = false) Integer teamId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(name)){
            params.put("name",name);
        }
        if(patientId!=null){
            params.put("patientId",patientId);
        }
        if(userId!=null){
            params.put("userId",userId);
        }
        if(teamId!=null){
            params.put("teamId",teamId);
        }
        IPage<PlanVo> page = appPlanService.PageVoList(new Page<>(current,pageSize),params);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }

    @ApiOperation("新增计划")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") PlanVo data){
        if (data.getIds().length < 1) {
            return R.error("请添加患者");
        }else{
            appPlanService.create(data);
            return R.ok();
        }
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return appPlanService.deletePlan(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("终止计划")
    @PostMapping("/deletePlan/{ids}")
    public R terminationPlan(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return appPlanService.terminationPlan(ids)?R.ok():R.error("终止计划错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info", appPlanService.getById(id));
    }
    @ApiOperation("通过计划id获取详情")
    @GetMapping("/readByPatient")
    public R readByPatient(@ApiParam("计划id")Integer id){
        return appPlanService.readByPatient(id);
    }
}
