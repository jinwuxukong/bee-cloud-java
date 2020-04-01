package com.hutu.modules.app.center.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.center.entity.ScaleTypeVo;
import com.hutu.modules.app.center.service.ScaleTypeService;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.common.entity.Doctor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 量表状态查询
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */

@Api(tags = "App-量表状态")
@RestController
@RequestMapping("AppScaleType")
public class ScaleTypeController {
    @Autowired
    private ScaleTypeService scaleTypeService;

    @ApiOperation("待完成量表")
    @GetMapping("/getNoFinishPage/{current}/{pageSize}")
    public R getNoFinishPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("量表名称") @RequestParam(required = false) String name,
                     @ApiParam("量表标签") @RequestParam(required = false) Integer formType) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(name)){
            params.put("name",name);
        }
        if(formType!=null){
            params.put("formType",formType);
        }
        Integer userId = JwtUtils.getUserId();
        if(userId!=null){
            params.put("userId",userId);
        }
        IPage<ScaleTypeVo> page =  scaleTypeService.noFinishList(new Page<>(current, pageSize),params);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("已完成量表")
    @GetMapping("/getFinishPage/{current}/{pageSize}")
    public R getFinishPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("量表名称") @RequestParam(required = false) String name,
                     @ApiParam("量表标签") @RequestParam(required = false) Integer formType) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(name)){
            params.put("name",name);
        }
        if(formType!=null){
            params.put("formType",formType);
        }
        Integer userId = JwtUtils.getUserId();
        if(userId!=null){
            params.put("userId",userId);
        }
        IPage<ScaleTypeVo> page =  scaleTypeService.FinishPage(new Page<>(current, pageSize),params);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("未按时提交量表")
    @GetMapping("/getoverTimePage/{current}/{pageSize}")
    public R getoverTimePage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("量表名称") @RequestParam(required = false) String name,
                     @ApiParam("量表标签") @RequestParam(required = false) Integer formType) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(name)){
            params.put("name",name);
        }
        if(formType!=null){
            params.put("formType",formType);
        }
        Integer userId = JwtUtils.getUserId();
        if(userId!=null){
            params.put("userId",userId);
        }
        IPage<ScaleTypeVo> page =  scaleTypeService.overTimePage(new Page<>(current, pageSize),params);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }
}
