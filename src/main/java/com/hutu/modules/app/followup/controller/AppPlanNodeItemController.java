package com.hutu.modules.app.followup.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.center.entity.ScaleTypeVo;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.app.followup.service.AppPlanNodeItemService;
import com.hutu.modules.app.followup.service.PatientService;
import com.hutu.modules.common.entity.PlanNodeItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 随访计划节点子表 App
 * </p>
 *
 * @author generator
 * @since 2019-10-15
 */

@Api(tags = "App随访计划节点子表")
@RestController
@RequestMapping("AppPlanNodeItem")
public class AppPlanNodeItemController {
    @Autowired
    private AppPlanNodeItemService appPlanNodeItemService;
    @Autowired
    private PatientService patientService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("量表名称") @RequestParam(required = false) String name,
                     @ApiParam("量表状态") @RequestParam(required = false) Integer status,
                     @ApiParam("量表标签") @RequestParam(required = false) Integer formType) {
        QueryWrapper<PlanNodeItem> queryWrapper = new QueryWrapper<>();
        Page<PlanNodeItem> page=new Page<>(current,pageSize);
        if (StrUtil.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if(status!=null){
            queryWrapper.eq("status",status);
        }
        if(formType!=null){
            queryWrapper.eq("formType",formType);
        }
        queryWrapper.eq("createId",JwtUtils.getUserId());
        appPlanNodeItemService.page(page,queryWrapper);
        List<PlanNodeItem> records = page.getRecords();
        List<ScaleTypeVo> scaleTypeVos = new ArrayList<ScaleTypeVo>();
        for (PlanNodeItem record : records) {
            ScaleTypeVo scaleTypeVo = new ScaleTypeVo();
            PatientVo patientVo = patientService.read(record.getPatientId(), JwtUtils.getUserId());
            scaleTypeVo.setAge((int)patientVo.getBirthday().until(LocalDate.now(), ChronoUnit.YEARS));
            scaleTypeVos.add(scaleTypeVo);
        }
        return R.ok().put("list",scaleTypeVos).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")PlanNodeItem data){
        return appPlanNodeItemService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return appPlanNodeItemService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    @AuthIgnore
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        PlanNodeItem planNodeItem = new PlanNodeItem().setId(Integer.valueOf(id));
        return R.ok().put("info", appPlanNodeItemService.getOneByid(planNodeItem));
    }

    @ApiOperation("通过对象获取列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") PlanNodeItem data){
        return R.ok().put("info",appPlanNodeItemService.getList(data));
    }

    @ApiOperation("修改短信通知状态为已通知")
    @GetMapping("/updateInformState/{id}")
    public R updateInformState(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info", appPlanNodeItemService.update(new UpdateWrapper<PlanNodeItem>().set("informState",1).eq("id",id)));
    }

    @ApiOperation("微信表单修改已读状态")
    @GetMapping("/UpdateRead/{id}")
    @AuthIgnore
    public R UpdateRead(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info", appPlanNodeItemService.update(new UpdateWrapper<PlanNodeItem>().set("isWeChatRead",1).set("status",1).eq("id",id)));
    }

}
