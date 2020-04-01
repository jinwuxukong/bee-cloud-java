package com.hutu.modules.admin.followup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.admin.followup.vo.AdminStatisticsSolutionVo;
import com.hutu.modules.admin.followup.vo.AdminStatisticsTeamVo;
import com.hutu.modules.admin.followup.vo.SolutionFrom;
import com.hutu.modules.admin.followup.vo.SolutionNodeFrom;
import com.hutu.modules.common.entity.Solution;
import com.hutu.modules.admin.followup.service.AdminSolutionService;
import com.hutu.modules.common.entity.SolutionNodeItem;
import com.hutu.modules.common.entity.Team;
import com.hutu.modules.common.entity.Template;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 方案表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */

@Api(tags = "随访运营端-方案管理")
@RestController
@RequestMapping("followupSolution")
public class AdminSolutionController {
    @Autowired
    private AdminSolutionService adminSolutionService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord,
                     @ApiParam("状态") @RequestParam(required = false) Integer status,
                     @ApiParam("适用科室") @RequestParam(required = false) String department,
                     @ApiParam("审核状态") @RequestParam(required = false) Integer verifyStatus) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StrUtil.isNotEmpty(keyWord)) {
            params.put("name", keyWord);
            params.put("createName", keyWord);
        }
        if (status!=null) {
            params.put("status", status);
        }
        if (StrUtil.isNotEmpty(department)) {
            params.put("department", department);
        }
        if (verifyStatus!=null) {
            params.put("verifyStatus", verifyStatus);
        }
        IPage<Solution> page = adminSolutionService.getPage(new Page<>(current,pageSize),params);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }


    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") Solution data) {
        return adminSolutionService.createOrUpdate(data);
    }

    @ApiOperation("分享")
    @GetMapping("/share/{id}/{scope}")
    public R share(@ApiParam("数据对象id") @PathVariable("id") Integer id,@ApiParam("平台类型") @PathVariable("scope") Integer scope) {
        SolutionFrom data = adminSolutionService.descById(id);
        //分享类型
        if(scope==1){
            data.getSolution().setScope(1);
            data.getSolution().setScopeShow("团队");
            data.getSolution().setVerifyStatus(0);
        }else if(scope==2){
            data.getSolution().setScope(2);
            data.getSolution().setScopeShow("平台");
            data.getSolution().setVerifyStatus(0);
        }else if(scope==0){
            data.getSolution().setScope(0);
            data.getSolution().setScopeShow("个人");
        }
        //清空id
        data.getSolution().setId(null);
        data.getSolution().setSolutionNumber(null);
        data.getSolution().setUpdateTime(LocalDateTime.now());
        for (SolutionNodeFrom solutionNodeFrom : data.getSolutionNodeList()) {
            solutionNodeFrom.setId(null);
            if(solutionNodeFrom.getVisitSolutionNodeItemList().size()>0){
                for (SolutionNodeItem solutionNodeItem : solutionNodeFrom.getVisitSolutionNodeItemList()) {
                    solutionNodeItem.setId(null);
                }
            }
            if(solutionNodeFrom.getFormSolutionNodeItemList().size()>0){
                for (SolutionNodeItem solutionNodeItem : solutionNodeFrom.getFormSolutionNodeItemList()) {
                    solutionNodeItem.setId(null);
                }
            }
        }
        return adminSolutionService.create(data);
    }

    @ApiOperation("新增版本")
    @PostMapping("/create")
    public R create(@RequestBody @ApiParam("数据对象") SolutionFrom data) {
        return adminSolutionService.create(data);
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return adminSolutionService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") String id) {
        return R.ok().put("info", adminSolutionService.getById(id));
    }

    @ApiOperation("获取方案详情")
    @GetMapping("/descById/{id}")
    public R descById(@ApiParam("数据对象id") @PathVariable("id") Integer id) {
        return R.ok().put("info", adminSolutionService.descById(id));
    }

    @ApiOperation("官方方案")
    @GetMapping("/officialSolution")
    public R officialSolution() {
        return R.ok().put("info", adminSolutionService.count(new QueryWrapper<Solution>().eq("scope",2)));
    }

    @ApiOperation("通过对象获取列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") Solution data){
        return R.ok().put("info",adminSolutionService.getList(data));
    }

    @ApiOperation("获取版本列表数据")
    @GetMapping("/getVersionList/{id}/{solutionNumber}")
    public R getVersionList(@ApiParam("数据对象id") @PathVariable("id") Integer id,@ApiParam("模板编号") @PathVariable("solutionNumber") String solutionNumber){
        return R.ok().put("info",adminSolutionService.getVersionList(id,solutionNumber));
    }

    @ApiOperation("待审核方案")
    @GetMapping("/checkPendingSolution")
    public R checkPendingSolution() {
        return R.ok().put("info", adminSolutionService.count(new QueryWrapper<Solution>().eq("verifyStatus",0)));
    }

    @ApiOperation("个人方案")
    @GetMapping("/personageSolution")
    public R personageSolution() {
        return R.ok().put("info", adminSolutionService.count(new QueryWrapper<Solution>().eq("scope",0).eq("autherId", JwtUtils.getUserId())));
    }

    @ApiOperation("统计")
    @GetMapping("/statistics")
    public R statistics(){
        AdminStatisticsSolutionVo adminStatisticsSolutionVo = new AdminStatisticsSolutionVo();
        adminStatisticsSolutionVo.setOfficialSolution(adminSolutionService.count(new QueryWrapper<Solution>().eq("scope",2)));
        adminStatisticsSolutionVo.setCheckPendingSolution(adminSolutionService.count(new QueryWrapper<Solution>().eq("verifyStatus",0)));
        adminStatisticsSolutionVo.setPersonageSolution(adminSolutionService.count(new QueryWrapper<Solution>().eq("scope",0).eq("autherId", JwtUtils.getUserId())));
        return R.ok().put("info", adminStatisticsSolutionVo);
    }

}
