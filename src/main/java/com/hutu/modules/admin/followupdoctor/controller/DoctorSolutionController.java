package com.hutu.modules.admin.followupdoctor.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.YimiUtil;
import com.hutu.modules.admin.followupdoctor.vo.DoctorSolutionVo;
import com.hutu.modules.common.entity.Solution;
import com.hutu.modules.admin.followupdoctor.service.DoctorSolutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 方案表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */

@Api(tags = "随访医生端-方案管理")
@RestController
@RequestMapping("doctorSolution")
public class DoctorSolutionController {
    @Autowired
    private DoctorSolutionService doctorSolutionService;

    @ApiOperation("获取我的方案page")
    @GetMapping("/my/page/{current}/{pageSize}")
    public R getMyPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                       @ApiParam("关键字") @RequestParam(required = false) String keyWord,
                       @ApiParam("启用状态") @RequestParam(required = false) Integer status,
                       @ApiParam("方案类型用,隔开") @RequestParam(required = false) String types) {
        Page<Solution> page = new Page<>(current, pageSize);
        doctorSolutionService.getMyPage(page, keyWord, status, types);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") Solution data) {
        //设置为个人
        data.setScope(0);
        data.setScopeShow("个人");
        //设置解决方案的编号
        if (data.getId() == null) {
            data.setSolutionNumber("FA-" + YimiUtil.getNumber("solutionNumber"));
        }
        return doctorSolutionService.createOrUpdate(data);
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return doctorSolutionService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") String id) {
        return R.ok().put("info", doctorSolutionService.getById(id));
    }

    @ApiOperation("平台方案page")
    @GetMapping("/plateForm/page/{current}/{pageSize}")
    public R getPlateFormPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                              @ApiParam("关键字") @RequestParam(required = false) String keyWord,
                              @ApiParam("启用状态") @RequestParam(required = false) Integer status,
                              @ApiParam("方案类型id用,隔开") @RequestParam(required = false) String types,
                              @ApiParam("范围（个人0，团队1，平台2）") @RequestParam(required = false) int scope) {
        Page page = new Page<>(current, pageSize);
        IPage pageResult = doctorSolutionService.getPlateFormPage(page, keyWord, status, types, scope);
        return R.ok().put("list", pageResult.getRecords()).put("total", pageResult.getTotal());
    }

    /**
     * scope 1 2
     * 将方案库中的方案添加到我自己的方案中
     * 拿到 方案id
     * 通过id去遍历下面关联的node nodeItem 以及量表
     * 把他们重新复制一份出来，将创建人和当前拥有者改为自己 scope 改为0
     */
    @ApiOperation("把平台的方案添加到自己方案中")
    @GetMapping("/addPlateFormSolutionToMe/{solutionId}")
    public R addPlateFormSolutionToMe(@ApiParam("当前页") @PathVariable("solutionId") int solutionId) {
        return doctorSolutionService.addPlateFormSolutionToMe(solutionId);
    }

}
