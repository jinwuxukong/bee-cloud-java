package com.hutu.modules.app.followup.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.followup.entity.PatientFormVo;
import com.hutu.modules.app.followup.entity.PlanNodeItemVo;
import com.hutu.modules.app.followup.service.AppPatientFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 直接随访患者表单表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-10-16
 */

@Api(tags = "App直接随访患者表单表")
@RestController
@RequestMapping("patientForm")
public class AppPatientFormController{
    @Autowired
    private AppPatientFormService patientFormService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("名称") @RequestParam(required = false) String name,
                     @ApiParam("模板类型") @RequestParam(required = false) Integer type,
                     @ApiParam("表单的类型") @RequestParam(required = false) Integer formType,
                     @ApiParam("平台类型") @RequestParam(required = false) Integer scope,
                     @ApiParam("患者id") @RequestParam(required = false) Integer patientId,
                     @ApiParam("团队id") @RequestParam(required = false) Integer teamId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StrUtil.isNotEmpty(name)) {
            params.put("name", name);
        }
        if (type!=null) {
            params.put("type", type);
        }
        if (formType!=null) {
            params.put("formType", formType);
        }
        if (scope!=null) {
            params.put("scope", scope);
        }
        if(patientId!=null){
            params.put("patientId", patientId);
        }
        if(teamId!=null){
            params.put("teamId",teamId);
        }
        params.put("doctorId", JwtUtils.getUserId());
        params.put("scaleType",1);
        IPage<PlanNodeItemVo> page = patientFormService.getPage(new Page<>(current,pageSize),params);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增直接随访")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("直接随访对象") PatientFormVo data) throws Exception {
        return patientFormService.createOrUpdate(data)?R.ok():R.error("发送失败请联系管理员");
    }
    @ApiOperation("患者填表单修改随访参数")
    @AuthIgnore
    @PostMapping("/UpdatePatientForm")
    public R UpdatePatientForm(@RequestBody @ApiParam("随访对象") PlanNodeItemVo data){
        return patientFormService.UpdatePatientForm(data)?R.ok():R.error("提交失败");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return patientFormService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
        @GetMapping("/read/{id}")
        public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
            return R.ok().put("info",patientFormService.getById(id));
    }
    @ApiOperation("通过患者ID获取一条数据")
    @GetMapping("/readByPatientId/{id}")
    public R readByPatient(@ApiParam("患者id")@PathVariable("id")String id){
        return R.ok().put("info",patientFormService.getByPatientId(id));
    }

}
