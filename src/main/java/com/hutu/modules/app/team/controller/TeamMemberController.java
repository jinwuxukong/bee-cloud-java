package com.hutu.modules.app.team.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.center.entity.UserVo;
import com.hutu.modules.app.center.service.MyCenterService;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.app.team.service.TeamMemberService;
import com.hutu.modules.app.team.service.TeamService;
import com.hutu.modules.common.entity.Team;
import com.hutu.modules.common.entity.TeamMember;
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

/**
 * 医生团队成员表 前端控制器
 *
 * @author generator
 * @since 2019-12-26
 */

@Api(tags = "医生团队成员表")
@RestController
@RequestMapping("teamMember")
public class TeamMemberController{
    @Autowired
    private TeamMemberService teamMemberService;
    @Autowired
    private MyCenterService myCenterService;
    @Autowired
    private TeamService teamService;

    /**
     * 团队医生成员列表
     * @param current
     * @param pageSize
     * @param teamId
     * @param verifyStatus 为空申请加入列表，为1成员列表
     * @return
     */
    @ApiOperation("团队医生成员列表")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("名称") @RequestParam(required = false) String name,
                     @ApiParam("团队id") @RequestParam(required = false) Integer teamId,
                     @ApiParam("成员绑定状态") @RequestParam(required = false) String verifyStatus) {
        Map<String, Object> params = new HashMap<String, Object>();
        Team team = null;
        if(teamId!=null){
            params.put("teamId",teamId);
            team = teamService.getOne(new QueryWrapper<Team>().eq("id", teamId));
        }
        if(StrUtil.isNotEmpty(verifyStatus)){
            params.put("verifyStatus",verifyStatus);
        }
        if(StrUtil.isNotEmpty(name)){
            params.put("name",name);
        }
        IPage<TeamMemberVo> page = teamMemberService.getPage(new Page<>(current,pageSize),params);
        if(StrUtil.isEmpty(verifyStatus)){
            List<TeamMemberVo> records = page.getRecords();
            for (int i = 0; i < records.size(); i++) {
                if(records.get(i).getUserId().equals(team.getOwnerId())){
                    if(records.size()==1){
                        return R.ok().put("list",new ArrayList<>()).put("total",page.getTotal());
                    }else{
                        records.remove(i);
                    }
                }
            }
        }
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")TeamMember data){
        return teamMemberService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("团队成员审核")
    @PostMapping("/teamMembers")
    public R teamMembers(@RequestBody @ApiParam("数据对象")TeamMember data){
        return teamMemberService.teamMembers(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("加入团队")
    @PostMapping("/addOrUpdate")
    public R addOrUpdate(@RequestBody @ApiParam("数据对象")TeamMember data){
        return teamMemberService.addOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return teamMemberService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",teamMemberService.getById(id));
    }
    @ApiOperation("通过对象获取列表数据")
    @PostMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") TeamMember data){
        return R.ok().put("info",teamMemberService.getList(data));
    }
    @ApiOperation("团队医生详情")
    @PostMapping("/getOne")
    public R getOne(@RequestBody @ApiParam("数据对象") TeamMember data){
        TeamMemberVo TeamMemberVo = teamMemberService.getOne(data);
        UserVo showInfo = myCenterService.getShowInfo(TeamMemberVo.getUserId());
        TeamMemberVo.setUserVo(showInfo);
        return R.ok().put("info",TeamMemberVo);
    }
}
