package com.hutu.modules.admin.followup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.admin.followup.service.FeedbackReplyService;
import com.hutu.modules.common.entity.FeedbackReply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * 意见反馈回复表 前端控制器
 *
 * @author generator
 * @since 2020-03-02
 */

@Api(tags = "随访运营端-意见反馈回复表")
@RestController
@RequestMapping("feedbackReply")
public class FeedbackReplyController{
    @Autowired
    private FeedbackReplyService feedbackReplyService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<FeedbackReply> query = new QueryWrapper<FeedbackReply>().like(StrUtil.isNotEmpty(keyWord), "name", keyWord);
        IPage<FeedbackReply> page = feedbackReplyService.page(new Page<>(current,pageSize),query);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")FeedbackReply data){
        return feedbackReplyService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return feedbackReplyService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",feedbackReplyService.getById(id));
    }
    @ApiOperation("通过对象获取列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") FeedbackReply data){
        return R.ok().put("info",feedbackReplyService.getList(data));
    }
    @ApiOperation("本周新增意见和反馈")
    @GetMapping("/getFeedbackWeek")
    public R getFeedbackWeek(){
        LocalDate todayOfLastWeek = LocalDate.now();
        LocalDate monday = todayOfLastWeek.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
        LocalDate sunday = todayOfLastWeek.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1);
        //当天最小时间
        LocalDateTime today_start = LocalDateTime.of(monday, LocalTime.MIN);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //当天最大时间
        LocalDateTime today_end = LocalDateTime.of(sunday, LocalTime.MAX);
        DateTimeFormatter df_end = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return R.ok().put("info",feedbackReplyService.count(new QueryWrapper<FeedbackReply>().between("createTime",df.format(today_start),df_end.format(today_end))));
    }

    @ApiOperation("本年新增意见和反馈")
    @GetMapping("/getFeedbackYear")
    public R getFeedbackYear(){
        return R.ok().put("info",feedbackReplyService.getFeedbackYear());
    }
}
