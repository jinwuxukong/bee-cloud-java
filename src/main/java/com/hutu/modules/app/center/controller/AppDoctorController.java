package com.hutu.modules.app.center.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.app.center.service.AppDoctorService;
import com.hutu.modules.app.center.service.MyCenterService;
import com.hutu.modules.app.followup.service.PatientService;
import com.hutu.modules.common.entity.Doctor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 医生信息表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */

@Api(tags = "App-医生管理")
@RestController
@RequestMapping("AppDoctor")
public class AppDoctorController {
    @Autowired(required = false)
    private AppDoctorService appDoctorService;
    @Autowired
    private MyCenterService myCenterService;
    @Autowired
    private PatientService patientService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("姓名") @RequestParam(required = false) String name,
                     @ApiParam("团队") @RequestParam(required = false) Integer teamId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(name)){
            params.put("name",name);
        }
        if(teamId!=null){
            params.put("teamId",teamId);
        }
        IPage<TeamMemberVo> page = appDoctorService.getPage(new Page<>(current,pageSize), params);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") Doctor data) {
        return appDoctorService.saveOrUpdate(data) ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("医生审核")
    @PostMapping("/createVerifier")
    public R createOrUpdateVerifier(@RequestBody @ApiParam("数据对象") Doctor data) {
        if(data.getVerifyStatus()==3){
            data.setVerifyNum(1);
        }
        data.setVerifyStatus(2);
        return appDoctorService.update(data,new UpdateWrapper<Doctor>().eq(data!=null,"id",data.getId())) ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return appDoctorService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("修改默认团队")
    @PostMapping("/setDefaultTeam/{teamId}/{teamName}")
    public R setDefaultTeam(@ApiParam("团队id") @PathVariable("teamId")Integer teamId,@ApiParam("团队名称") @PathVariable("teamName")String teamName) {
        return R.ok().put("info", appDoctorService.setDefaultTeam(JwtUtils.getUserId(),teamId,teamName));
    }

    @ApiOperation("获取一条数据")
    @GetMapping("/read")
    public R read() {
        return R.ok().put("info", appDoctorService.getOne(new QueryWrapper<Doctor>().eq("userId", JwtUtils.getUserId())));
    }

}
