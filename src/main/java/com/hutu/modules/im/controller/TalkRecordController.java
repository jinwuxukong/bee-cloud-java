package com.hutu.modules.im.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.im.entity.TalkRecord;
import com.hutu.modules.im.service.TalkRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-09-25
 */

@Api(tags = "即时通讯相关接口")
@RestController
@RequestMapping("talkRecord")
public class TalkRecordController{
    @Autowired
    private TalkRecordService talkRecordService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("最新记录id")@RequestParam(required = false) String recordId,@ApiParam("接受者id") String toUserId) {
        Integer fromUserId = 111;
        Page<TalkRecord> page=new Page<>(current,pageSize);
        talkRecordService.page(page,new QueryWrapper<TalkRecord>().and(i->i.eq("fromUserId", fromUserId).eq("toUserId", toUserId).or().eq("fromUserId", toUserId).eq("toUserId", fromUserId)).gt(StrUtil.isNotEmpty(recordId), "id", recordId).orderByDesc("createTime"));
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")TalkRecord data){
        return talkRecordService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return talkRecordService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",talkRecordService.getById(id));
    }

}
