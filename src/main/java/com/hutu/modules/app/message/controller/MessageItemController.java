package com.hutu.modules.app.message.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.app.followup.service.PatientService;
import com.hutu.modules.app.message.entity.MessageItemVo;
import com.hutu.modules.app.message.service.MessageItemService;
import com.hutu.modules.common.entity.MessageItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 个人消息表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-10-21
 */

@Api(tags = "个人消息表")
@RestController
@RequestMapping("messageItem")
public class MessageItemController{
    @Autowired
    private MessageItemService messageItemService;
    @Autowired
    private PatientService patientService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("一级类型") @RequestParam(required = false) Integer type,
                     @ApiParam("二级类型") @RequestParam(required = false) Integer parameterType,
                     @ApiParam("消息类型") @RequestParam(required = false) Integer messageType,
                     @ApiParam("消息id") @RequestParam(required = false) Integer messageId) {
        QueryWrapper<MessageItem> queryWrapper = new QueryWrapper<>();
        Page<MessageItem> page=new Page<>(current,pageSize);
        if(messageType!=null){
            if(messageType==1){
                queryWrapper.eq("type",type)
                        .eq("parameterType",parameterType)
                        .eq("toUserId", JwtUtils.getUserId())
                        .orderByDesc("createTime");
            }else if(messageType==2){
                queryWrapper.eq("type",type)
                        .eq("parameterType",parameterType)
                        .eq(messageId!=null,"messageId",messageId)
                        .orderByDesc("createTime");
            }
        }
        messageItemService.page(page,queryWrapper);
        List<MessageItem> messageItem = page.getRecords();
        ArrayList<MessageItemVo> messageItemVos = new ArrayList<>();
        if(parameterType==3){
            for (MessageItem item : messageItem) {
                MessageItemVo messageItemVo = new MessageItemVo();
                BeanUtils.copyProperties(item,messageItemVo);
                PatientVo read = patientService.read(Integer.parseInt(item.getParameter()), JwtUtils.getUserId());
                messageItemVo.setPatient(read);
                messageItemVos.add(messageItemVo);
            }
        }else{
            for (MessageItem item : messageItem) {
                MessageItemVo messageItemVo = new MessageItemVo();
                BeanUtils.copyProperties(item,messageItemVo);
                messageItemVos.add(messageItemVo);
            }
        }
        return R.ok().put("list",messageItemVos).put("total",page.getTotal());
    }
    @ApiOperation("新增")
    @PostMapping("/create")
    public R create(@RequestBody @ApiParam("数据对象") MessageItem data){
        return messageItemService.create(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return messageItemService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",messageItemService.getById(id));
    }
    @ApiOperation("标记为已读")
    @GetMapping("/readUpdateByid/{id}")
    public R readUpdateByid(@ApiParam("数据对象id")@PathVariable("id")String id){
        return messageItemService.readUpdateByid(id)?R.ok():R.error("已读设置失败");
    }
    @ApiOperation("标记全部为已读")
    @GetMapping("/readUpdateByIds")
    public R readUpdateByIds(@ApiParam("一级类型")Integer type,@ApiParam("二级类型")Integer parameterType){
        return messageItemService.readUpdateByIds(type,parameterType,null)?R.ok():R.error("已读设置失败");
    }

}
