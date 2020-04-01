package com.hutu.modules.app.team.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.center.entity.UserVo;
import com.hutu.modules.app.center.service.AppDoctorService;
import com.hutu.modules.app.center.service.MyCenterService;
import com.hutu.modules.app.team.entity.TeamVo;
import com.hutu.modules.app.team.service.TeamService;
import com.hutu.modules.common.entity.Doctor;
import com.hutu.modules.common.entity.Team;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 医生团队表 前端控制器
 *
 * @author generator
 * @since 2019-12-26
 */

@Api(tags = "医生团队表")
@RestController
@RequestMapping("team")
public class TeamController{
    @Autowired
    private TeamService teamService;
    @Autowired
    private MyCenterService myCenterService;
    @Autowired
    private AppDoctorService appDoctorService;

    @ApiOperation("团队匹配分页")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("医院名") @RequestParam(required = false) String hospital) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(hospital)){
            params.put("hospital",hospital);
        }
        params.put("userId",JwtUtils.getUserId());
        IPage<TeamVo> page = teamService.getPage(new Page<>(current,pageSize),params);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }

    @ApiOperation("我的团队分页")
    @GetMapping("/applyPage/{current}/{pageSize}")
    public R applyPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                       @ApiParam("审核状态") @RequestParam(required = false) Integer verifyStatus,
                       @ApiParam("是否查我创建的团队") @RequestParam(required = false) Integer type) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId",JwtUtils.getUserId());
        List<Team> team = teamService.list(new QueryWrapper<Team>().eq("ownerId", JwtUtils.getUserId()));
        if(verifyStatus!=null){
            params.put("verifyStatus",verifyStatus);
        }
        if(type!=null){
            params.put("type",type);
        }
        IPage<TeamVo> page = teamService.applyPage(new Page<>(current,pageSize),params);
        List<TeamVo> records = page.getRecords();
        if(team.size()>0&&verifyStatus==null){
            if(records.size()>0){
                for (int i = 0; i < records.size(); i++) {
                    for (int j = 0; j < team.size(); j++) {
                        if(records.get(i).getOwnerId().equals(team.get(j).getOwnerId())){
                            if(records.size()==1){
                                return R.ok().put("list",new ArrayList<>()).put("total",page.getTotal());
                            }else{
                                records.remove(i);
                            }
                        }
                    }
                }
            }
        }else{
            Doctor doctor = appDoctorService.getOne(new QueryWrapper<Doctor>().eq("userId", JwtUtils.getUserId()));
            if(doctor!=null){
                if(records.size()>0){
                    for (int i = 0; i < records.size(); i++) {
                        if(records.get(i).getId().equals(doctor.getDoctorTeamId())){
                            if(records.size()==1){
                                return R.ok().put("list",new ArrayList<>()).put("total",page.getTotal());
                            }else{
                                records.remove(i);
                            }
                        }
                    }
                }
            }
        }
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")Team data){
        return teamService.createOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return teamService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",teamService.getById(id));
    }
    @ApiOperation("通过对象获取列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象")Team data){
        return R.ok().put("info",teamService.getList(data));
    }

    @ApiOperation("成员消息全部变为已读")
    @GetMapping("/isRead/{teamId}/{parameterType}")
    public R isRead(@ApiParam("团队id")@PathVariable("teamId")Integer teamId,@ApiParam("消息二级类型")@PathVariable("parameterType")Integer parameterType){
        return R.ok().put("info",teamService.isRead(teamId,parameterType));
    }

    @ApiOperation("获取是否是团队责任人")
    @GetMapping("/getLevel/{teamId}")
    public R getLevel(@ApiParam("团队id")@PathVariable("teamId")Integer teamId){
        Team team = teamService.getOne(new QueryWrapper<Team>().eq("id", teamId));
        if(team.getOwnerId().equals(JwtUtils.getUserId())){
            return R.ok().put("info",true);
        }else{
            return R.ok().put("info",false);
        }
    }

    @ApiOperation("团队创建名称校验")
    @GetMapping("/teamVerify/{teamName}")
    public R isRead(@ApiParam("团队名称")@PathVariable("teamName")String teamName){
        return R.ok().put("info",teamService.getOne(new QueryWrapper<Team>().eq("name",teamName))!=null?true:false);
    }

    @ApiOperation("通过医生id获取默认关注团队信息")
    @GetMapping("/getDefaultTeamInfo/{doctorId}")
    public R getDefaultTeamInfo(@ApiParam("医生id") @PathVariable("doctorId") String doctorId) {
        UserVo showInfo = myCenterService.getShowInfo(Integer.valueOf(doctorId));
        if (showInfo == null) {
            return R.error("查无此医生, id为： " + doctorId);
        }
        if (showInfo.getDoctor().getDoctorTeamId() == null) {
            return R.ok().put("info", null);
        }
        Team team = teamService.getById(showInfo.getDoctor().getDoctorTeamId());
        return R.ok().put("info", team);
    }
}
