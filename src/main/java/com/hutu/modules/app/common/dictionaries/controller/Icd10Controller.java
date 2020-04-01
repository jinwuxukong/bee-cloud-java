package com.hutu.modules.app.common.dictionaries.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.app.common.dictionaries.service.Icd10Service;
import com.hutu.modules.common.entity.Icd10;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * icd10字典表 前端控制器
 *
 * @author generator
 * @since 2019-12-05
 */

@Api(tags = "icd10字典表")
@RestController
@RequestMapping("icd10")
public class Icd10Controller{
    @Autowired
    private Icd10Service icd10Service;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Icd10> query = new QueryWrapper<Icd10>().like(StrUtil.isNotEmpty(keyWord), "name", keyWord);
        IPage<Icd10> page = icd10Service.page(new Page<>(current,pageSize),query);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")Icd10 data){
        return icd10Service.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return icd10Service.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",icd10Service.getById(id));
    }

    @ApiOperation("疾病列表")
    @GetMapping("/icd10List")
    public R icd10List(@ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Icd10> query = new QueryWrapper<Icd10>().like(StrUtil.isNotEmpty(keyWord), "name", keyWord);
        String sql="limit 10";
        query.last(sql);
        query.select("id","name","icd10");
        List<Icd10> list = icd10Service.list(query);
        return R.ok().put("list",list);
    }
}
