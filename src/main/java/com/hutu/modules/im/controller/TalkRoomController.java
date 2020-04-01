package com.hutu.modules.im.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.im.entity.TalkRoom;
import com.hutu.modules.im.service.TalkRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 聊天记录表 前端控制器
 *
 * @author generator
 * @since 2019-11-27
 */

@Api(tags = "聊天记录表")
@RestController
@RequestMapping("talkRoom")
public class TalkRoomController{
    @Autowired
    private TalkRoomService talkRoomService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<TalkRoom> query = new QueryWrapper<TalkRoom>().like(StrUtil.isNotEmpty(keyWord), "name", keyWord);
        IPage<TalkRoom> page = talkRoomService.page(new Page<>(current,pageSize),query);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")TalkRoom data){
        return talkRoomService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("添加组员")
    @PostMapping("/addMember")
    public R addMember(@RequestBody @ApiParam("数据对象")TalkRoom data){
        TalkRoom talkRoom = talkRoomService.getById(data.getId());
        String groupUserIds = talkRoom.getGroupUserIds();
        return talkRoomService.update(new UpdateWrapper<TalkRoom>().set("groupUserIds",groupUserIds+data.getGroupUserIds()).eq("id",data.getId()))?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return talkRoomService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",talkRoomService.getById(id));
    }
}
