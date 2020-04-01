package com.hutu.modules.admin.login.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.modules.admin.login.service.InitiationInfoService;
import com.hutu.modules.common.entity.InitiationInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 入会详情 前端控制器
 *
 * @author generator
 * @since 2019-11-18
 */

@Api(tags = "入会详情")
@RestController
@RequestMapping("initiationInfo")
public class InitiationInfoController{
    @Autowired
    private InitiationInfoService initiationInfoService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<InitiationInfo> query = new QueryWrapper<InitiationInfo>().like(StrUtil.isNotEmpty(keyWord), "name", keyWord);
        IPage<InitiationInfo> page = initiationInfoService.page(new Page<>(current,pageSize),query);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @AuthIgnore
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")InitiationInfo data){
        return initiationInfoService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return initiationInfoService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",initiationInfoService.getById(id));
    }
}
