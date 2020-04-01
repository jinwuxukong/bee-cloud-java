package com.hutu.modules.app.team.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.message.entity.MessageFrom;
import com.hutu.modules.app.message.service.MessageService;
import com.hutu.modules.app.team.service.TeamMemberService;
import com.hutu.modules.app.team.service.TeamMessageService;
import com.hutu.modules.common.entity.Message;
import com.hutu.modules.common.entity.Team;
import com.hutu.modules.common.entity.TeamMember;
import com.hutu.modules.common.entity.TeamMessage;
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
 * 系统消息表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-10-21
 */

@Api(tags = "团队一级列表")
@RestController
@RequestMapping("TeamMessageInform")
public class TeamMessageInformController {
    @Autowired
    private TeamMessageService messageService;
    @Autowired
    private TeamMemberService teamMemberService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("团队Id") @RequestParam(required = false) Integer teamId) {
        QueryWrapper<TeamMessage> queryWrapper = new QueryWrapper<>();
        Page<TeamMessage> page=new Page<>(current,pageSize);
        if(teamId!=null){
            queryWrapper.eq("teamId",teamId);
            //团队权限
            TeamMember teamMember = teamMemberService.getOne(new QueryWrapper<TeamMember>().eq("userId", JwtUtils.getUserId()).eq("teamId", teamId));
            if(teamMember.getType()==1){
                queryWrapper.in("parameterType",5,6,7,8,10,11,12);
            }
        }
        messageService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增")
    @PostMapping("/create")
    public R create(@RequestBody @ApiParam("数据对象")TeamMessage data){
        return messageService.create(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("消息创建")
    @PostMapping("/createMessage")
    public R createMessage(@RequestBody @ApiParam("数据对象") MessageFrom data){
        return messageService.createMessage(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("修改")
    @PostMapping("/update")
    public R updateByid(@RequestBody @ApiParam("数据对象")TeamMessage data){
        return messageService.updateByid(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return messageService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",messageService.getById(id));
    }
    @ApiOperation("通过ID获取是否有消息")
    @GetMapping("/isMessage/{teamId}")
    public R isMessage(@ApiParam("团队id")@PathVariable("teamId") Integer teamId){
        Map<String, Object> params = new HashMap<String, Object>();
        TeamMember teamMember = teamMemberService.getOne(new QueryWrapper<TeamMember>().eq("userId", JwtUtils.getUserId()).eq("teamId", teamId));
        if(teamMember.getType()==1){
            int[] in = {5,6,7,8,10,11,12};
            params.put("parameterType",in);
        }
        if(teamId!=null){
            params.put("teamId",teamId);
        }
        Integer count = messageService.isMessage(params);
        if(count!=null&&count>0){
            return R.ok().put("info",true);
        }else{
            return R.ok().put("info",false);
        }
    }

}
