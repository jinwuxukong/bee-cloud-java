package com.hutu.modules.admin.followupdoctor.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.admin.followupdoctor.service.DoctorSurveyService;
import com.hutu.modules.common.entity.Survey;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 表单填写记录表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-07-11
 */

@Api(tags = "随访医生端-表单填写记录表")
@RestController
@RequestMapping("doctorSurvey")
public class DoctorSurveyController {
    @Autowired
    private DoctorSurveyService doctorSurveyService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Survey> queryWrapper = new QueryWrapper<>();
        Page<Survey> page=new Page<>(current,pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        doctorSurveyService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R create(@RequestBody @ApiParam("数据对象")Survey data){
        return doctorSurveyService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("保存表单调查结果")
    @PostMapping("/save")
    public R save(@RequestBody @ApiParam("数据对象") String data){
        return doctorSurveyService.saveResult(data) ? R.ok() : R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return doctorSurveyService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",doctorSurveyService.getById(id));
    }

}
