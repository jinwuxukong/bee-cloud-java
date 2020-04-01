package com.hutu.modules.admin.followup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.admin.followup.vo.AdminStatisticsTeamVo;
import com.hutu.modules.admin.followup.vo.AdminTeamVo;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.common.entity.Team;
import com.hutu.modules.admin.followup.service.AdminTeamService;
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
 * 医生团队表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */

@Api(tags = "随访运营端-医生团队管理")
@RestController
@RequestMapping("followupTeam")
public class AdminTeamController {
    @Autowired
    private AdminTeamService adminTeamService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord,
                     @ApiParam("审核状态") @RequestParam(required = false) Integer verifyStatus) {
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
        Page<Team> page=new Page<>(current,pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        if(verifyStatus!=null){
            queryWrapper.eq("verifyStatus",verifyStatus);
        }
        adminTeamService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }

    @ApiOperation("获取医生团队信息")
    @GetMapping("/DoctorTeamPage/{current}/{pageSize}")
    public R DoctorTeamPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("医生id") @RequestParam(required = false) String doctorId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("doctorId", doctorId);
        IPage<AdminTeamVo> page = adminTeamService.DoctorTeamPage(new Page<>(current, pageSize), params);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")Team data){
        return adminTeamService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return adminTeamService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info", adminTeamService.getById(id));
    }
    @ApiOperation("待审核团队")
    @GetMapping("/checkPendingTeam")
    public R checkPendingTeam(){
        return R.ok().put("info", adminTeamService.count(new QueryWrapper<Team>().eq("verifyStatus",0)));
    }
    @ApiOperation("院内团队")
    @GetMapping("/hospitalTeam")
    public R hospitalTeam(){
        return R.ok().put("info", adminTeamService.count(new QueryWrapper<Team>().eq("type",1)));
    }
    @ApiOperation("名医团队")
    @GetMapping("/MedicalTeam")
    public R MedicalTeam(){
        return R.ok().put("info", adminTeamService.count(new QueryWrapper<Team>().eq("type",2)));
    }

    @ApiOperation("统计")
    @GetMapping("/statistics")
    public R statistics(){
        AdminStatisticsTeamVo adminStatisticsTeamVo = new AdminStatisticsTeamVo();
        adminStatisticsTeamVo.setCheckPendingTeam(adminTeamService.count(new QueryWrapper<Team>().eq("verifyStatus",0)));
        adminStatisticsTeamVo.setHospitalTeam(adminTeamService.count(new QueryWrapper<Team>().eq("type",1)));
        adminStatisticsTeamVo.setMedicalTeam(adminTeamService.count(new QueryWrapper<Team>().eq("type",2)));
        return R.ok().put("info", adminStatisticsTeamVo);
    }
}
