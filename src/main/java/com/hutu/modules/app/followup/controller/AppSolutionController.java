package com.hutu.modules.app.followup.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.followup.service.AppPlanService;
import com.hutu.modules.app.followup.service.AppSolutionService;
import com.hutu.modules.common.entity.Solution;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 方案表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */

@Api(tags = "App-方案管理")
@RestController
@RequestMapping("AppFollowupSolution")
public class AppSolutionController {
    @Autowired
    private AppSolutionService appSolutionService;
    @Autowired
    private AppPlanService appPlanService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("名称")@RequestParam(required = false) String keyWord,
                     @ApiParam("方案类型") @RequestParam(required = false)String type,
                     @ApiParam("平台类型") @RequestParam(required = false)String scope) {
        QueryWrapper<Solution> queryWrapper = new QueryWrapper<>();
        QueryWrapper<Solution> queryWrapper2 = new QueryWrapper<>();
        Page<Solution> page = new Page<>(current, pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
            queryWrapper2.like("name", keyWord);
        }
        if (StrUtil.isNotEmpty(type)) {
            queryWrapper.in("type", Arrays.asList(type.split(",")));
            queryWrapper2.in("type", Arrays.asList(type.split(",")));
        }
        queryWrapper.orderByDesc("createTime");
        queryWrapper2.orderByDesc("createTime");
        List<Solution> solution = new ArrayList<>();
        if (scope.equals("2")) {
            queryWrapper.eq("scope", 2);
            solution = appSolutionService.page(page, queryWrapper).getRecords();
        }else if(scope.equals("1")){

        }else if(scope.equals("0")){
            queryWrapper2.eq("currentOwnerId",JwtUtils.getUserId());
            queryWrapper2.eq("scope", 0);
            List<Solution> solution1 = appSolutionService.page(page, queryWrapper2).getRecords();
            solution.addAll(solution1);
        }else{
            queryWrapper.eq("scope", 2);
            List<Solution> solution2 = appSolutionService.page(page, queryWrapper).getRecords();
            queryWrapper2.eq("currentOwnerId",JwtUtils.getUserId());
            queryWrapper2.eq("scope", 0);
            List<Solution> solution1 = appSolutionService.page(page, queryWrapper2).getRecords();
            solution.addAll(solution1);
            solution.addAll(solution2);
        }
        return R.ok().put("list", solution).put("total", page.getTotal());
    }

    /* @ApiOperation("新增或更新")
     @PostMapping("/createOrUpdate")
     public R createOrUpdate(@RequestBody @ApiParam("数据对象") Solution data) {
         return solutionService.saveOrUpdate(data) ? R.ok() : R.error("保存错误");
     }*/
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") Solution data) {
        return appSolutionService.createOrUpdate(data);
    }

    @ApiOperation("我的方案统计")
    @GetMapping("/statistics")
    public R statistics() {
        return R.ok().put("info", appSolutionService.countStatistics(JwtUtils.getUserId(),0));
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return appSolutionService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过方案ID获取一条方案详情")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") Integer id) {
        return R.ok().put("info", appSolutionService.readById(id));
    }

    @ApiOperation("通过对象获取列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") Solution data){
        return R.ok().put("info",appSolutionService.getList(data));
    }

}
