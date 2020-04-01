package com.hutu.modules.admin.followupdoctor.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.constant.Constant;
import com.hutu.common.entity.R;
import com.hutu.common.util.YimiUtil;
import com.hutu.modules.admin.followupdoctor.service.DoctorTemplateService;
import com.hutu.modules.common.entity.Template;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 表单模板表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */

@Api(tags = "随访医生端-模板管理")
@RestController
@RequestMapping("doctorTemplate")
public class DoctorTemplateController {
    @Autowired
    private DoctorTemplateService doctorTemplateService;

    @ApiOperation("获取page")
    @GetMapping("/my/page/{current}/{pageSize}")
    public R getMyPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                       @ApiParam("关键字") @RequestParam(required = false) String keyWord,
                       @ApiParam("模板类型") @RequestParam(value = "type") String type,
                       @ApiParam("表单类型 ids，用 , 隔开") @RequestParam(value = "formTypes", required = false) String formTypes) {
        Page<Template> page = new Page<>(current, pageSize);
        doctorTemplateService.getMyPage(page, keyWord, type, formTypes);
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
            //按照 , 分开
            queryWrapper.in("formType", Arrays.asList(formTypes.split(",")));
        }
        if (StrUtil.isNotEmpty(patientEducationTypes) && StrUtil.isNotBlank(patientEducationTypes)) {
            //按照 , 分开
            queryWrapper.in("patientEducationType", Arrays.asList(patientEducationTypes.split(",")));
        }
        return R.ok().put("list", doctorTemplateService.list(queryWrapper));
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R create(@RequestBody @ApiParam("数据对象") Template data) {
        if (data.getId() == null) {
            if (data.getType() == 1) {
                data.setTemplateNumber("LB-" + YimiUtil.getNumber("templateNumber"));
            } else {
                data.setTemplateNumber("XJ-" + YimiUtil.getNumber("templateNumber"));
            }
            data.setScope(1);
            data.setScopeShow("个人");
        }
        boolean b = doctorTemplateService.saveOrUpdate(data);
        return b ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return doctorTemplateService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") String id) {
        return R.ok().put("info", doctorTemplateService.getById(id));
    }

    @ApiOperation("获取平台量表page")
    @GetMapping("/plateForm/page/{current}/{pageSize}")
    public R getPlateFormPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                              @ApiParam("关键字") @RequestParam(required = false) String keyWord,
                              @ApiParam("模板类型") @RequestParam(value = "type") String type,
                              @ApiParam("表单类型 ids，用 , 隔开") @RequestParam(value = "formTypes", required = false) String formTypes,
                              @ApiParam("共享范围") @RequestParam(value = "scope", required = false) Integer scope) {
        Page<Template> page = new Page<>(current, pageSize);
        doctorTemplateService.getPlateFormPage(page, keyWord, type, formTypes, scope);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("将模板库中的共享模板添加为我的")
    @PostMapping("/addToMyTemplate/{id}")
    public R addToMyTemplate(@ApiParam("id") @PathVariable("id") int id) {
        return doctorTemplateService.addToMyTemplate(id);
    }

}
