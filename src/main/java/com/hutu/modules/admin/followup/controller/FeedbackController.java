package com.hutu.modules.admin.followup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.admin.followup.service.FeedbackReplyService;
import com.hutu.modules.admin.followup.service.FeedbackService;
import com.hutu.modules.admin.followup.vo.AdminStatisticsFeedbackVo;
import com.hutu.modules.admin.followup.vo.FeedbackVo;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.common.entity.Feedback;
import com.hutu.modules.common.entity.FeedbackReply;
import com.hutu.modules.common.entity.Team;
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
import java.util.HashMap;
import java.util.Map;

/**
 * 意见反馈表 前端控制器
 *
 * @author generator
 * @since 2020-03-02
 */

@Api(tags = "随访运营端-意见反馈表")
@RestController
@RequestMapping("feedback")
public class FeedbackController{
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private FeedbackReplyService feedbackReplyService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord,
                     @ApiParam("状态") @RequestParam(required = false) Integer status) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(keyWord)){
            params.put("sourceName",keyWord);
            params.put("content",keyWord);
        }
        if(status!=null){
            params.put("status",status);
        }
        IPage<Feedback> page = feedbackService.getPage(new Page<>(current,pageSize),params);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")Feedback data){
        return feedbackService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return feedbackService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        Feedback feedback = feedbackService.getById(id);
        FeedbackVo feedbackVo = new FeedbackVo();
        feedbackVo.setFeedback(feedback);
        feedbackVo.setFeedbackReply(feedbackReplyService.getOne(new QueryWrapper<FeedbackReply>().eq("feedbackId",feedback.getId())));
        return R.ok().put("info",feedbackVo);
    }
    @ApiOperation("通过对象获取列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") Feedback data){
        return R.ok().put("info",feedbackService.getList(data));
    }
    @ApiOperation("待处理意见个数")
    @GetMapping("/getFeedbackNum")
    public R getFeedbackNum(){
        return R.ok().put("info",feedbackService.count(new QueryWrapper<Feedback>().eq("status",0)));
    }
    @ApiOperation("统计")
    @GetMapping("/statistics")
    public R statistics(){
        AdminStatisticsFeedbackVo adminStatisticsFeedbackVo = new AdminStatisticsFeedbackVo();
        adminStatisticsFeedbackVo.setGetFeedbackNum(feedbackService.count(new QueryWrapper<Feedback>().eq("status",0)));
        LocalDate todayOfLastWeek = LocalDate.now();
        LocalDate monday = todayOfLastWeek.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
        LocalDate sunday = todayOfLastWeek.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1);
        //当天最小时间
        LocalDateTime today_start = LocalDateTime.of(monday, LocalTime.MIN);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //当天最大时间
        LocalDateTime today_end = LocalDateTime.of(sunday, LocalTime.MAX);
        DateTimeFormatter df_end = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        adminStatisticsFeedbackVo.setGetFeedbackWeek(feedbackReplyService.count(new QueryWrapper<FeedbackReply>().between("createTime",df.format(today_start),df_end.format(today_end))));
        adminStatisticsFeedbackVo.setGetFeedbackYear(feedbackReplyService.getFeedbackYear());
        return R.ok().put("info",adminStatisticsFeedbackVo);
    }
}
