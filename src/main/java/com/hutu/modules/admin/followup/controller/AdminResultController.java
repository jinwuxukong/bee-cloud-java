package com.hutu.modules.admin.followup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.common.entity.Result;
import com.hutu.modules.admin.followup.service.AdminResultService;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 表单结果表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */

@Api(tags = "随访运营端-表单结果表")
@RestController
@RequestMapping("adminResult")
public class AdminResultController {
    @Autowired
    private AdminResultService resultService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Result> queryWrapper = new QueryWrapper<>();
        Page<Result> page=new Page<>(current,pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        resultService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("获取表单结果集")
    @GetMapping("/list/template/{templateId}")
    public R getPage(@ApiParam("模板id")@PathVariable("templateId")@NotNull int templateId) {
        List<Result> results = resultService.list(new QueryWrapper<Result>().eq("templateId", templateId));
        return R.ok().put("list",results);
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R create(@RequestBody @ApiParam("数据对象")Result data){
        return resultService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return resultService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",resultService.getById(id));
    }

}
