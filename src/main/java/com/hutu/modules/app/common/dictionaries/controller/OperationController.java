package com.hutu.modules.app.common.dictionaries.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.app.common.dictionaries.service.OperationService;
import com.hutu.modules.common.entity.Operation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 手术术式表 前端控制器
 *
 * @author generator
 * @since 2019-12-05
 */

@Api(tags = "手术术式表")
@RestController
@RequestMapping("operation")
public class OperationController{
    @Autowired
    private OperationService operationService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Operation> query = new QueryWrapper<Operation>().like(StrUtil.isNotEmpty(keyWord), "name", keyWord);
        IPage<Operation> page = operationService.page(new Page<>(current,pageSize),query);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")Operation data){
        return operationService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return operationService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",operationService.getById(id));
    }

    @ApiOperation("获取page")
    @GetMapping("/operationList")
    public R operationList(@ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Operation> query = new QueryWrapper<Operation>().like(StrUtil.isNotEmpty(keyWord), "name", keyWord);
        String sql="limit 10";
        query.last(sql);
        query.select("id","name");
        List<Operation> list = operationService.list(query);
        return R.ok().put("list",list);
    }
}
