package com.hutu.modules.app.followup.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.app.followup.entity.PatientCasesVo;
import com.hutu.modules.app.followup.service.PatientCasesService;
import com.hutu.modules.app.followup.service.PatientService;
import com.hutu.modules.common.entity.Patient;
import com.hutu.modules.common.entity.PatientCases;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 患者病历信息 前端控制器
 *
 * @author generator
 * @since 2020-01-09
 */

@Api(tags = "患者病历信息")
@RestController
@RequestMapping("patientCases")
public class PatientCasesController{
    @Autowired
    private PatientCasesService patientCasesService;
    @Autowired
    private PatientService patientService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("患者id") @RequestParam(required = false) Integer patientId,
                     @ApiParam("就诊时间开始") @RequestParam(required = false) String visitTimeStart,
                     @ApiParam("就诊时间结束") @RequestParam(required = false) String visitTimeEnd,
                     @ApiParam("科室名称") @RequestParam(required = false) String departmentName) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(patientId!=null){
            params.put("patientId",patientId);
        }
        if(visitTimeStart!=null){
            params.put("visitTimeStart",visitTimeStart);
        }
        if(visitTimeEnd!=null){
            params.put("visitTimeEnd",visitTimeEnd);
        }
        if(StrUtil.isNotEmpty(departmentName)){
            params.put("departmentName",departmentName);
        }
        IPage<PatientCases> page = patientCasesService.getPage(new Page<>(current,pageSize),params);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")PatientCases data){
        return patientCasesService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return patientCasesService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",patientCasesService.getById(id));
    }
    @ApiOperation("患者病例列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") PatientCases data){
        return R.ok().put("info",patientCasesService.getList(data));
    }
}
