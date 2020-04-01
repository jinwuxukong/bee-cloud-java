package com.hutu.modules.app.common.dictionaries.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.modules.app.common.dictionaries.service.HospitalService;
import com.hutu.modules.common.entity.Hospital;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 全国知名医院信息表 前端控制器
 *
 * @author generator
 * @since 2019-12-05
 */

@Api(tags = "全国知名医院信息表")
@RestController
@RequestMapping("hospital")
public class HospitalController{
    @Autowired
    private HospitalService hospitalService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Hospital> query = new QueryWrapper<Hospital>().like(StrUtil.isNotEmpty(keyWord), "name", keyWord);
        IPage<Hospital> page = hospitalService.page(new Page<>(current,pageSize),query);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")Hospital data){
        return hospitalService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return hospitalService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",hospitalService.getById(id));
    }

    @ApiOperation("获取省份page")
    @GetMapping("/provincePage/{current}/{pageSize}")
    public R getProvincePage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("省份名称") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Hospital> query = new QueryWrapper<Hospital>().like(StrUtil.isNotEmpty(keyWord), "province", keyWord);
        query.groupBy("province");
        IPage<Hospital> page = hospitalService.page(new Page<>(current,pageSize),query);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }

    @ApiOperation("获取市区page")
    @GetMapping("/cityPage/{current}/{pageSize}")
    public R getcityPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                         @ApiParam("市区名称") @RequestParam(required = false) String keyWord,
                         @ApiParam("省份名称") @RequestParam(required = false) String province) {
        QueryWrapper<Hospital> query = new QueryWrapper<Hospital>().eq(StrUtil.isNotEmpty(province), "province", province);
        query.like(StrUtil.isNotEmpty(keyWord),"city",keyWord);
        IPage<Hospital> page = hospitalService.page(new Page<>(current,pageSize),query);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }

    @AuthIgnore
    @ApiOperation("获取医院List")
    @GetMapping("/hospitalList")
    public R getHospitalPage(@ApiParam("医院名称") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Hospital> query = new QueryWrapper<Hospital>();
        query.like(StrUtil.isNotEmpty(keyWord),"name",keyWord);
        String sql="limit 10";
        query.last(sql);
        query.select("id","name","type");
        List<Hospital> list = hospitalService.list(query);
        return R.ok().put("list",list);
    }
}
