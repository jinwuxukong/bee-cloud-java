package com.hutu.modules.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hutu.modules.app.followup.service.AppPlanNodeItemService;
import com.hutu.modules.app.followup.service.AppPlanNodeService;
import com.hutu.modules.app.followup.service.AppPlanService;
import com.hutu.modules.common.entity.Plan;
import com.hutu.modules.common.entity.PlanNode;
import com.hutu.modules.common.entity.PlanNodeItem;
import com.hutu.modules.rest.service.JeewxService;
import com.hutu.modules.task.service.FollowupTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * 随访定时任务
 *
 * @author hutu
 * @date 2020-01-02 19:26
 */
@Service
@Slf4j
public class FollowupTaskServiceImpl implements FollowupTaskService {

    @Autowired
    private AppPlanNodeItemService appPlanNodeItemService;
    @Autowired
    private AppPlanNodeService appPlanNodeService;
    @Autowired
    private AppPlanService appPlanService;
    @Autowired
    private JeewxService jeewxService;

    /**
     * 给患者推送今天的表单
     * item状态 0 未推送，1 已推送，2，已填写/已阅
     *
     * @return 是否执行成功
     */
    @Override
    public boolean send() {
        // 查询今天需要发送的随访
        List<PlanNode> planNodes = appPlanNodeService.list(new QueryWrapper<PlanNode>().eq("executeDate", LocalDate.now()).eq("status", 0));
        // 给患者推随访表单
        for (PlanNode planNode : planNodes) {
            List<PlanNodeItem> planNodeItems = appPlanNodeItemService.list(new QueryWrapper<PlanNodeItem>().eq("nodeId", planNode.getId()));
            for (PlanNodeItem planNodeItem : planNodeItems) {
                boolean b = jeewxService.sendFormTemplateMessage(planNodeItem);
                if (b) {
                    // 表单状态改为已发送
                    appPlanNodeItemService.updateById(planNodeItem.setIsSendWeChat(1));
                } else {
                    log.error("----表单发送失败 详情： {}", planNodeItem.toString());
                }
            }
            // 更新随访计划的状态
            Plan plan = appPlanService.getById(planNode.getFollowupPlanId());
            if (plan.getStatus().equals(0)) {
                appPlanService.updateById(plan.setStatus(1));
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean timeout() {
        // 延时天数
        int days = 3;
        // 刷临时随访
        appPlanNodeItemService.update(new UpdateWrapper<PlanNodeItem>().set("status", 2).eq("scaleType", 1).eq("status", 0).le("createTime", LocalDate.now().minusDays(days)));

        // 刷计划随访
        // 查出节点超时计划节点
        List<PlanNode> planNodes = appPlanNodeService.list(new QueryWrapper<PlanNode>().eq("status", 0).le("executeDate", LocalDate.now().minusDays(days)));
        // 没有则直接返回
        if(planNodes==null||planNodes.size()==0){
            return true;
        }
        ArrayList<Integer> planNodeIds = new ArrayList<>();
        planNodes.forEach(obj -> planNodeIds.add(obj.getId()));
        // 更新子节点
        appPlanNodeItemService.update(new UpdateWrapper<PlanNodeItem>().set("status", 2).eq("status", 0).in("nodeId", planNodeIds));

        return true;
    }

}
