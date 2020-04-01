package com.hutu.modules.admin.followup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.common.util.YimiUtil;
import com.hutu.modules.admin.followup.vo.DoctorVo;
import com.hutu.modules.common.entity.Doctor;
import com.hutu.modules.common.entity.Template;
import com.hutu.modules.admin.followup.service.AdminTemplateService;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 表单模板表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */

@Api(tags = "随访运营端-表单模板表")
@RestController
@RequestMapping("template")
public class AdminTemplateController {
    @Autowired
    private AdminTemplateService templateService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("名称") @RequestParam(required = false) String name,
                     @ApiParam("模板类型") @RequestParam(required = false) Integer type,
                     @ApiParam("状态") @RequestParam(required = false) Integer status,
                     @ApiParam("审核状态") @RequestParam(required = false) Integer verifyStatus,
                     @ApiParam("模板编号") @RequestParam(required = false) String templateNumber,
                     @ApiParam("科室") @RequestParam(required = false) Integer formType,
                     @ApiParam("平台范围") @RequestParam(required = false) Integer scope) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StrUtil.isNotEmpty(name)) {
            params.put("name", name);
        }
        if (StrUtil.isNotEmpty(templateNumber)) {
            params.put("templateNumber", templateNumber);
        }
        if (type!=null) {
            params.put("type", type);
        }
        if (status!=null) {
            params.put("status", status);
        }
        if (verifyStatus!=null) {
            params.put("verifyStatus", verifyStatus);
        }
        if(formType!=null){
            params.put("formType", formType);
        }
        if(scope!=null){
            params.put("scope", scope);
        }
        IPage<Template> page = templateService.getPage(new Page<>(current,pageSize),params);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("根据模板类型和（表单类型/宣教类型）")
    @GetMapping("/getAll/{type}")
    public R getPage(@ApiParam("模板类型") @PathVariable("type") int type,
                     @ApiParam("表单类型,表单的类型字符串，用 , 隔开") @RequestParam(required = false) String formTypes) {
        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type).eq("status", 1);
        if (StrUtil.isNotEmpty(formTypes) && StrUtil.isNotBlank(formTypes)) {
            queryWrapper.in("formType", formTypes.split(","));
        }
        return R.ok().put("list", templateService.list(queryWrapper));
    }

    @ApiOperation("新增")
    @PostMapping("/create")
    public R create(@RequestBody @ApiParam("数据对象") Template data) {
        if (data.getId() == null) {
            if (data.getType() == 1) {
                data.setTemplateNumber("LB-" + YimiUtil.getNumber("templateNumber"));
            } else if(data.getType() == 2){
                data.setTemplateNumber("XJ-" + YimiUtil.getNumber("templateNumber"));
            }
            //若是管理员，那么就将模板scope的状态置为2
            if(JwtUtils.getCallerInfo().type.equals(0)){
                data.setScope(2);
                data.setScopeShow("平台");
                data.setVerifyId(JwtUtils.getUserId());
                data.setVerifyName(JwtUtils.getUserName());
                data.setVerifyTime(LocalDateTime.now());
                data.setVerifyStatus(1);
            }else{
                data.setScope(0);
                data.setScopeShow("个人");
                data.setVerifyId(JwtUtils.getUserId());
                data.setVerifyName(JwtUtils.getUserName());
                data.setVerifyTime(LocalDateTime.now());
                data.setVerifyStatus(1);
            }
        }
        data.setVersion(1.0f);
        boolean b = templateService.save(data);
        return b ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("分享")
    @PostMapping("/share")
    public R share(@RequestBody @ApiParam("数据对象") Template data) {
        if (data==null){
            return R.error("请求空数据");
        }
        //清空id
        data.setId(null);
        data.setTemplateNumber(null);
        if(data.getScope()==1){
            data.setScope(1);
            data.setScopeShow("团队");
            data.setVerifyStatus(0);
        }else if(data.getScope()==2){
            data.setScope(2);
            data.setScopeShow("平台");
            data.setVerifyId(null);
            data.setVerifyName(null);
            data.setVerifyTime(null);
            data.setVerifyStatus(0);
        }else if(data.getScope()==0){
            data.setScope(0);
            data.setScopeShow("个人");
        }
        if (data.getType() == 1) {
            data.setTemplateNumber("LB-" + YimiUtil.getNumber("templateNumber"));
        }
        data.setVersion(1.0f);
        data.setUpdateTime(LocalDateTime.now());
        boolean b = templateService.save(data);
        return b ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") Template data) {
        if(data.getVerifyStatus()!=null && data.getVerifyStatus()==1){
            data.setVerifyId(JwtUtils.getUserId());
            data.setVerifyName(JwtUtils.getUserName());
            data.setVerifyTime(LocalDateTime.now());
        }else if(data.getVerifyStatus()!=null && data.getVerifyStatus()==2){
            data.setVerifyId(JwtUtils.getUserId());
            data.setVerifyName(JwtUtils.getUserName());
            data.setVerifyTime(LocalDateTime.now());
        }
        return templateService.saveOrUpdate(data) ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("新建版本")
    @PostMapping("/newVersion")
    @Transactional(rollbackFor = Exception.class)
    public synchronized R newVersion(@RequestBody @ApiParam("数据对象") Template data) {
        boolean update = templateService.update(new UpdateWrapper<Template>().set("status", 0).eq("templateNumber", data.getTemplateNumber()));
        data.setVersion(templateService.getVersion(data.getTemplateNumber())+1);
        data.setVerifyId(JwtUtils.getUserId());
        data.setVerifyName(JwtUtils.getUserName());
        data.setVerifyTime(LocalDateTime.now());
        data.setStatus(1);
        data.setId(null);
        boolean b = templateService.save(data);
        return b ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return templateService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") String id) {
        return R.ok().put("info", templateService.getById(id));
    }

    @ApiOperation("获取版本列表数据")
    @GetMapping("/getVersionList/{id}/{templateNumber}")
    public R getVersionList(@ApiParam("数据对象id") @PathVariable("id") Integer id,@ApiParam("模板编号") @PathVariable("templateNumber") String templateNumber){
        return R.ok().put("info",templateService.getVersionList(id,templateNumber));
    }

    @ApiOperation("通过对象获取列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") Template data){
        return R.ok().put("info",templateService.getList(data));
    }
}
