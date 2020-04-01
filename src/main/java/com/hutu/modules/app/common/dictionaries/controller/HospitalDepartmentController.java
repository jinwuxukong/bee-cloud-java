package com.hutu.modules.app.common.dictionaries.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.modules.app.common.entity.HospitalDepartmentVo;
import com.hutu.modules.app.common.dictionaries.service.HospitalDepartmentService;
import com.hutu.modules.common.entity.HospitalDepartment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 医院科室表 前端控制器
 *
 * @author generator
 * @since 2019-12-05
 */

@Api(tags = "医院科室表")
@RestController
@RequestMapping("hospitalDepartment")
public class HospitalDepartmentController{
    @Autowired
    private HospitalDepartmentService hospitalDepartmentService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<HospitalDepartment> query = new QueryWrapper<HospitalDepartment>().like(StrUtil.isNotEmpty(keyWord), "name", keyWord);
        query.eq("type",1);
        IPage<HospitalDepartment> page = hospitalDepartmentService.page(new Page<>(current,pageSize),query);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")HospitalDepartment data){
        return hospitalDepartmentService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return hospitalDepartmentService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",hospitalDepartmentService.getById(id));
    }

    @ApiOperation("获取所有数据")
    @AuthIgnore
    @GetMapping("/All")
    public R All(){
        List<HospitalDepartment> HospitalDepartment = hospitalDepartmentService.list();
        List<HospitalDepartmentVo> hospitalDepartmentVos = new ArrayList<>();
        for (HospitalDepartment hospitalDepartment : HospitalDepartment) {
            if(hospitalDepartment.getType()==1){
                HospitalDepartmentVo hospitalDepartmentVo = new HospitalDepartmentVo();
                hospitalDepartmentVo.setHospitalDepartmentList(new ArrayList<HospitalDepartment>());
                BeanUtils.copyProperties(hospitalDepartment,hospitalDepartmentVo);
                hospitalDepartmentVos.add(hospitalDepartmentVo);
            }
        }
        for (HospitalDepartmentVo hospitalDepartmentVo : hospitalDepartmentVos) {
            for (HospitalDepartment hospitalDepartment : HospitalDepartment) {
                if(hospitalDepartmentVo.getId()==hospitalDepartment.getParentId()){
                    hospitalDepartmentVo.getHospitalDepartmentList().add(hospitalDepartment);
                }
            }
        }
        return R.ok().put("info",hospitalDepartmentVos);
    }
}
