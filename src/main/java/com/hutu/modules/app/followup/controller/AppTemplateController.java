package com.hutu.modules.app.followup.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.followup.service.AppTemplateService;
import com.hutu.modules.common.entity.Template;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 表单模板表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */

@Api(tags = "App-表单模板表")
@RestController
@RequestMapping("Apptemplate")
public class AppTemplateController {
    @Autowired
    private AppTemplateService appTemplateService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord,
                     @ApiParam("模板类型") @RequestParam(required = false) String type,
                     @ApiParam("标签类型") @RequestParam(required = false) String formType) {
        Page<Template> page = new Page<>(current, pageSize);
        appTemplateService.getPage(page,keyWord,type,formType,JwtUtils.getUserId());
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("根据模板类型和（表单类型/宣教类型）")
    @GetMapping("/getAll/{type}")
    public R getPage(@ApiParam("模板类型") @PathVariable("type") int type,
                     @ApiParam("表单类型,表单的类型字符串，用 , 隔开") @RequestParam(required = false) String formTypes,
                     @ApiParam("宣教类型,宣教的类型字符串，用 , 隔开") @RequestParam(required = false) String patientEducationTypes) {
        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type).eq("status", 1);
        if (StrUtil.isNotEmpty(formTypes) && StrUtil.isNotBlank(formTypes)) {
            queryWrapper.in("formType", formTypes.split(","));
        }
        if (StrUtil.isNotEmpty(patientEducationTypes) && StrUtil.isNotBlank(patientEducationTypes)) {
            queryWrapper.in("patientEducationType", patientEducationTypes.split(","));
        }
        return R.ok().put("list", appTemplateService.list(queryWrapper));
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R create(@RequestBody @ApiParam("数据对象") Template data) {
        boolean b = appTemplateService.saveOrUpdate(data);
        return b ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return appTemplateService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @AuthIgnore
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") String id) {
        return R.ok().put("info", appTemplateService.getById(id));
    }

    @ApiOperation("通过对象获取列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") Template data){
        return R.ok().put("info",appTemplateService.getList(data));
    }
}
