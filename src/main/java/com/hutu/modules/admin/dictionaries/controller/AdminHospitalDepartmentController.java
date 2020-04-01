package com.hutu.modules.admin.dictionaries.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.TreeUtil;
import com.hutu.modules.admin.dictionaries.entity.AdminHospitalDepartmentVo;
import com.hutu.modules.admin.dictionaries.service.AdminHospitalDepartmentService;
import com.hutu.modules.common.entity.HospitalDepartment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医院科室表 前端控制器
 *
 * @author generator
 * @since 2019-12-05
 */

@Api(tags = "Admin医院科室表")
@RestController
@RequestMapping("adminHospitalDepartment")
public class AdminHospitalDepartmentController {
    @Autowired
    private AdminHospitalDepartmentService hospitalDepartmentService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(keyWord)){
            params.put("name",keyWord);
            current = 1;
            pageSize = 999;
        }else{
            params.put("type",1);
        }
        IPage<AdminHospitalDepartmentVo> page = hospitalDepartmentService.pageVo(new Page<>(current,pageSize),params);
        if (page.getRecords()==null && page.getRecords().size()==0){
            return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
        }
        if(StrUtil.isEmpty(keyWord)){
            List<AdminHospitalDepartmentVo> records = page.getRecords();
            List<Integer> ids = new ArrayList<>();
            for (AdminHospitalDepartmentVo record : records) {
                if(record.getType()==1){
                    record.setChildren(new ArrayList<>());
                    Integer id = record.getId();
                    ids.add(id);
                }
            }
            List<HospitalDepartment> parentId = hospitalDepartmentService.list(new QueryWrapper<HospitalDepartment>().in("parentId", ids));
            for (AdminHospitalDepartmentVo record : records) {
                for (HospitalDepartment hospitalDepartment : parentId) {
                    if(hospitalDepartment.getParentId()==record.getId()){
                        record.getChildren().add(hospitalDepartment);
                    }
                }
            }
        }else{
            List<AdminHospitalDepartmentVo> records = page.getRecords();
            List<HospitalDepartment> AdminHospitalDepartmentVos = new ArrayList<>();
            ArrayList<AdminHospitalDepartmentVo> adminHospitalDepartmentVos = new ArrayList<>();
            for (AdminHospitalDepartmentVo record : records) {
                if(record.getType()==2){
                    HospitalDepartment hospitalDepartment = new HospitalDepartment();
                    BeanUtils.copyProperties(record,hospitalDepartment);
                    AdminHospitalDepartmentVos.add(hospitalDepartment);
                    adminHospitalDepartmentVos.add(record);
                }else{
                    record.setChildren(new ArrayList<HospitalDepartment>());
                }
            }
            for (AdminHospitalDepartmentVo adminHospitalDepartmentVo : adminHospitalDepartmentVos) {
                records.remove(adminHospitalDepartmentVo);
            }
            for (AdminHospitalDepartmentVo record : records) {
                for (HospitalDepartment hospitalDepartment : AdminHospitalDepartmentVos) {
                    if(hospitalDepartment.getParentId()==record.getId()){
                        record.getChildren().add(hospitalDepartment);
                    }
                }
            }
        }
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
    @GetMapping("/All")
    public R All(@ApiParam("一级参数") @RequestParam(required = false) String name){
        List<HospitalDepartment> HospitalDepartment = hospitalDepartmentService.list(new QueryWrapper<HospitalDepartment>().like(StrUtil.isNotEmpty(name),"name",name));
        List<AdminHospitalDepartmentVo> hospitalDepartmentVos = new ArrayList<>();
        for (HospitalDepartment hospitalDepartment : HospitalDepartment) {
            if(hospitalDepartment.getType()==1){
                AdminHospitalDepartmentVo hospitalDepartmentVo = new AdminHospitalDepartmentVo();
                hospitalDepartmentVo.setChildren(new ArrayList<HospitalDepartment>());
                BeanUtils.copyProperties(hospitalDepartment,hospitalDepartmentVo);
                hospitalDepartmentVos.add(hospitalDepartmentVo);
            }
        }
        for (AdminHospitalDepartmentVo hospitalDepartmentVo : hospitalDepartmentVos) {
            for (HospitalDepartment hospitalDepartment : HospitalDepartment) {
                if(hospitalDepartmentVo.getId()==hospitalDepartment.getParentId()){
                    hospitalDepartmentVo.getChildren().add(hospitalDepartment);
                }
            }
        }
        return R.ok().put("info",hospitalDepartmentVos);
    }

    @ApiOperation("二叉树列表")
    @GetMapping("/binaryTree")
    public R binaryTree(){
        return R.ok().put("info",hospitalDepartmentService.binaryTree());
    }
}
