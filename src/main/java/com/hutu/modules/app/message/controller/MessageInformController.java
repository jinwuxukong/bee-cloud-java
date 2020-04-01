package com.hutu.modules.app.message.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.constant.Constant;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.message.entity.MessageFrom;
import com.hutu.modules.app.message.service.MessageService;
import com.hutu.modules.app.team.service.TeamMemberService;
import com.hutu.modules.common.entity.Message;
import com.hutu.modules.common.entity.TeamMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 系统消息表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-10-21
 */

@Api(tags = "系统消息表")
@RestController
@RequestMapping("messageInform")
public class MessageInformController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private TeamMemberService teamMemberService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("标题") @RequestParam(required = false) String title,
                     @ApiParam("消息类型") @RequestParam(required = false) Integer type,
                     @ApiParam("团队Id") @RequestParam(required = false) Integer teamId) {
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        Page<Message> page=new Page<>(current,pageSize);
        if (StrUtil.isNotEmpty(title)) {
            queryWrapper.like("title", title);
        }
        if(type==1){
            queryWrapper.isNull("teamId");
            queryWrapper.eq("toUserId", JwtUtils.getUserId());
        }
        if(type==2){
            queryWrapper.isNotNull("teamId");
            if(teamId!=null){
                queryWrapper.eq("teamId",teamId);
                //团队权限
                TeamMember teamMember = teamMemberService.getOne(new QueryWrapper<TeamMember>().eq("userId", JwtUtils.getUserId()).eq("teamId", teamId));
                if(teamMember.getType()==1){
                    queryWrapper.in("parameterType",5,6,7,8,10,11,12);
                }
            }
        }
        messageService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增")
    @PostMapping("/create")
    public R create(@RequestBody @ApiParam("数据对象")Message data){
        return messageService.create(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("消息创建")
    @PostMapping("/createMessage")
    public R createMessage(@RequestBody @ApiParam("数据对象") MessageFrom data){
        return messageService.createMessage(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("修改")
    @PostMapping("/update")
    public R updateByid(@RequestBody @ApiParam("数据对象")Message data){
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
    @GetMapping("/isMessage/{type}")
    public R isMessage(@ApiParam("消息类型")@PathVariable("type")Integer type,@ApiParam("团队id") Integer teamId){
        QueryWrapper<Message> qw = new QueryWrapper<Message>();
        if(type==1){
            qw.eq("toUserId", JwtUtils.getUserId());
            qw.isNull("teamId");
        }else if(type==2){
            qw.isNotNull("teamId");
            qw.eq("teamId",teamId);
        }
        List<Message> messageList = messageService.list(qw);
        if(!messageList.isEmpty()){
            int num = 0;
            for (Message message : messageList) {
                num = num + message.getStatusNum();
            }
            return R.ok().put("info",num>0?true:false);
        }else{
            return R.ok().put("info",false);
        }
    }

}
