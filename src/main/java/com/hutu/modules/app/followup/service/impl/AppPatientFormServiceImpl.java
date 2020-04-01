package com.hutu.modules.app.followup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.common.constant.Constant;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.followup.entity.*;
import com.hutu.modules.app.followup.mapper.AppPlanNodeItemMapper;
import com.hutu.modules.app.followup.service.*;
import com.hutu.modules.app.message.entity.MessageFrom;
import com.hutu.modules.app.message.service.MessageItemService;
import com.hutu.modules.app.message.service.MessageService;
import com.hutu.modules.common.entity.*;
import com.hutu.modules.rest.service.JeewxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 直接随访患者表单表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-16
 */
@Service
@Slf4j
public class AppPatientFormServiceImpl extends ServiceImpl<AppPlanNodeItemMapper, PlanNodeItem> implements AppPatientFormService {

    @Autowired
    private AppTemplateService appTemplateService;
    @Autowired
    private AppPlanService appPlanService;
    @Autowired
    private AppPlanNodeService appPlanNodeService;
    @Autowired
    private AppPlanNodeItemService appPlanNodeItemService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AppPlanNodeItemMapper appPlanNodeItemMapper;
    @Autowired
    private JeewxService jeewxService;
    @Autowired
    private PatientService appPatientService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createOrUpdate(PatientFormVo data) throws Exception {
        Integer userId = JwtUtils.getUserId();
        PatientVo patient = appPatientService.read(data.getPatientId(),userId);
        if(patient.getIsBinding()==0){
            return false;
        }
        Template template = appTemplateService.getById(data.getTemplateId());
        PlanNodeItem planNodeItem = new PlanNodeItem();
        planNodeItem.setName(template.getName());
        planNodeItem.setTemplateId(template.getId());
        planNodeItem.setTeamId(data.getTeamId());
        planNodeItem.setStatus(0);
        planNodeItem.setInformState(0);
        planNodeItem.setInformStateShow("未通知");
        planNodeItem.setScaleType(1);
        planNodeItem.setScaleTypeShow("直接随访");
        planNodeItem.setPatientId(patient.getId());
        planNodeItem.setDoctorId(patient.getDoctorUserId());
        planNodeItem.setPatientName(patient.getName());
        planNodeItem.setPhone(patient.getPhone());
        appPlanNodeItemService.save(planNodeItem);
        boolean b = jeewxService.sendFormTemplateMessage(planNodeItem);
        boolean update = appPlanNodeItemService.update(new UpdateWrapper<PlanNodeItem>().set("isSendWeChat", 1).eq("id", planNodeItem.getId()));
        if(b && update){
            log.info("===================表单发送成功===================");
            return b;
        }else{
            log.info("===================表单发送失败===================");
            throw new Exception("表单发送失败");
        }
    }

    @Override
    public List<PlanNodeItem> getByPatientId(String id) {
        return this.list(new QueryWrapper<PlanNodeItem>().eq("patientId", id));
    }

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public boolean UpdatePatientForm(PatientFormVo data) {
        Integer type = data.getType();
        if(type==1){
            PlanNodeItemVo byId = appPlanNodeItemService.getOneByid(new PlanNodeItem().setId(data.getId()));
            PatientVo doctorPatient = appPatientService.read(byId.getPatientId(),data.getDoctorUserId());
            this.update(new UpdateWrapper<PlanNodeItem>().set("answer",data.getAnswer()).set("status",data.getStatus()).eq("id",data.getId()));
            //异步发送通知消息
            if(byId.getType()==1){
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                CompletableFuture<String> messageCreate = CompletableFuture.supplyAsync(() -> {
                    try {
                        MessageFrom messageFrom = new MessageFrom();
                        messageFrom.setType(Constant.MESSAGE_SYSTEM);
                        messageFrom.setParameterType(Constant.MESSAGE_RECEIVED_SCALE);
                        messageFrom.setFirstTitle("已收到量表");
                        messageFrom.setContent(byId.getName());
                        messageFrom.setTitle(doctorPatient.getName());
                        messageFrom.setParameter(byId.getId().toString());
                        messageFrom.setToUserId(doctorPatient.getDoctorUserId());
                        messageService.createMessage(messageFrom);
                        return "===================推送消息成功===================";
                    } catch (Exception e) {
                        System.err.println("推送消息遭遇了不测");
                        System.err.println(e.getMessage());
                        return "===================推送消息失败===================";
                    }
                }, executorService);
                messageCreate.thenAccept((e) -> {
                    log.info(e);
                });
            }
            return true;
        }else{
            PlanNodeItem planNodeItem = appPlanNodeItemService.getById(data.getId());
            PlanNode planNode = appPlanNodeService.getById(planNodeItem.getNodeId());
            Plan plan = appPlanService.getById(planNode.getFollowupPlanId());
            int count = appPlanNodeItemService.count(new QueryWrapper<PlanNodeItem>().eq("status", 2).eq("nodeId", planNode.getId()).eq("type",1));
            int count2 = appPlanNodeItemService.count(new QueryWrapper<PlanNodeItem>().eq("nodeId", planNode.getId()).eq("type",1));
            //判断当前计划随访节点子节点是否是最后一个完成
            if(count==count2){
                appPlanNodeService.update(new UpdateWrapper<PlanNode>().set("status",1).eq("id",planNode.getId()));
            }
            List<PlanNode> planNodeList = appPlanNodeService.list(new QueryWrapper<PlanNode>().eq("followupPlanId", plan.getId()));
            int count3 = 0;
            int count4 = 0;
            for (PlanNode node : planNodeList) {
                count4 = count4+appPlanNodeItemService.count(new QueryWrapper<PlanNodeItem>().eq("status", 2).eq("nodeId", node.getId()).eq("type",1));
                count3 = count3+appPlanNodeItemService.count(new QueryWrapper<PlanNodeItem>().eq("nodeId", node.getId()).eq("type",1));
            }
            //判断当前计划随访所有节点子节点是否全部完成
            if(count3==count4){
                appPlanService.update(new UpdateWrapper<Plan>().set("status",2).eq("id",plan.getId()));
                PatientVo doctorPatient = appPatientService.read(planNodeItem.getPatientId(),planNodeItem.getDoctorId());
                //异步发送通知消息
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                CompletableFuture<String> messageCreate = CompletableFuture.supplyAsync(() -> {
                    try {
                        Integer userId = JwtUtils.getUserId();
                        MessageFrom messageFrom = new MessageFrom();
                        messageFrom.setType(Constant.MESSAGE_VISIT);
                        messageFrom.setParameterType(Constant.MESSAGE_VISIT_END);
                        messageFrom.setFirstTitle("随访计划結束");
                        messageFrom.setContent("【"+plan.getSolutionName()+"】");
                        messageFrom.setTitle(plan.getPatientName());
                        messageFrom.setParameter(plan.getId().toString());
                        messageFrom.setToUserId(doctorPatient.getDoctorUserId());
                        messageService.createMessage(messageFrom);
                        return "===================推送消息成功===================";
                    } catch (Exception e) {
                        System.err.println("推送消息遭遇了不测");
                        System.err.println(e.getMessage());
                        return "===================推送消息失败===================";
                    }
                }, executorService);
                messageCreate.thenAccept((e) -> {
                    log.info(e);
                });
                *//*if(planNodeItem.getType()==1){
                    CompletableFuture<String> messageCreate2 = CompletableFuture.supplyAsync(() -> {
                        try {
                            MessageFrom messageFrom = new MessageFrom();
                            messageFrom.setType(Constant.MESSAGE_SYSTEM);
                            messageFrom.setParameterType(Constant.MESSAGE_RECEIVED_SCALE);
                            messageFrom.setFirstTitle("已收到量表");
                            messageFrom.setContent(planNodeItem.getName());
                            messageFrom.setTitle(plan.getPatientName());
                            messageFrom.setParameter(planNodeItem.getId().toString());
                            messageFrom.setToUserId(doctorPatient.getDoctorUserId());
                            messageService.createMessage(messageFrom);
                            return "===================推送消息成功===================";
                        } catch (Exception e) {
                            System.err.println("推送消息遭遇了不测");
                            System.err.println(e.getMessage());
                            return "===================推送消息失败===================";
                        }
                    }, executorService);
                    messageCreate2.thenAccept((e) -> {
                        log.info(e);
                    });
                }*//*
            }
            return appPlanNodeItemService.update(new UpdateWrapper<PlanNodeItem>().set("answer", data.getAnswer()).set("status", data.getStatus()).eq("id", data.getId()));
        }
    }*/

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean UpdatePatientForm(PlanNodeItemVo data) {
        Integer type = data.getScaleType();
        PlanNodeItemVo planNodeItemVo = appPlanNodeItemService.getOneByid(new PlanNodeItem().setId(data.getId()));
        if(type==1){
            boolean update = appPlanNodeItemService.update(new UpdateWrapper<PlanNodeItem>().set("answer", data.getAnswer()).set("status", data.getStatus()).eq("id", data.getId()));
            if(planNodeItemVo.getScaleType()==1){
                ExecutorService executorService = Executors.newFixedThreadPool(1);
                CompletableFuture<String> messageCreate = CompletableFuture.supplyAsync(() -> {
                    try {
                        MessageFrom messageFrom = new MessageFrom();
                        messageFrom.setType(Constant.MESSAGE_SYSTEM);
                        messageFrom.setParameterType(Constant.MESSAGE_RECEIVED_SCALE);
                        messageFrom.setFirstTitle("已收到量表");
                        messageFrom.setContent(planNodeItemVo.getName());
                        messageFrom.setTitle(planNodeItemVo.getPatientName());
                        messageFrom.setParameter(planNodeItemVo.getId().toString());
                        messageFrom.setTeamId(data.getTeamId());
                        messageFrom.setToUserId(planNodeItemVo.getDoctorId());
                        messageService.createMessage(messageFrom);
                        return "===================推送消息成功===================";
                    } catch (Exception e) {
                        System.err.println("推送消息遭遇了不测");
                        System.err.println(e.getMessage());
                        return "===================推送消息失败===================";
                    }
                }, executorService);
                messageCreate.thenAccept((e) -> {
                    log.info(e);
                });
            }
            return update;
        }else{
            //修改计划随访节点子节点添加答案
            this.update(new UpdateWrapper<PlanNodeItem>().set("answer",data.getAnswer()).set("status",data.getStatus()).eq("id",data.getId()));
            //通过计划随访节点子节点获取计划
            Plan plan = appPlanService.getPlanByPlanNodeItem(planNodeItemVo.getNodeId());
            //通过计划获取计划详情
            PlanVo planVo = appPlanService.getOnePlan(plan);
            int planNodeCount = planVo.getPlanNodeVo().size();
            int planNodeOkCount = 0;
            for (PlanNodeVo planNodeVo : planVo.getPlanNodeVo()) {
                List<PlanNodeItemVo> planNodeItemVoList = new ArrayList<>();
                planNodeItemVoList.addAll(planNodeVo.getFormPlanNodeItemList());
                planNodeItemVoList.addAll(planNodeVo.getVisitPlanNodeItemList());
                //当前计划节点计划随访总数
                int planNodeItemVoCount = planNodeItemVoList.size();
                //当前计划节点计划随访完成总数
                int planNodeItemVoOkCount = 0;
                for (PlanNodeItemVo nodeItemVo : planNodeItemVoList) {
                    if(nodeItemVo.getStatus()==2){
                        planNodeItemVoOkCount++;
                    }
                }
                //判断当前计划随访节点子节点是否是最后一个完成
                if(planNodeItemVoCount==planNodeItemVoOkCount){
                    appPlanNodeService.update(new UpdateWrapper<PlanNode>().set("status",1).eq("id",planNodeVo.getId()));
                }
                if(planNodeVo.getStatus()==2){
                    planNodeOkCount++;
                }
            }
            //判断当前计划随访所有节点子节点是否全部完成
            if(planNodeCount==planNodeOkCount){
                //修改计划状态
                appPlanService.update(new UpdateWrapper<Plan>().set("status",2).eq("id",plan.getId()));
                //异步发送通知消息
                ExecutorService executorService = Executors.newFixedThreadPool(2);
                CompletableFuture<String> messageCreate = CompletableFuture.supplyAsync(() -> {
                    try {
                        MessageFrom messageFrom = new MessageFrom();
                        messageFrom.setType(Constant.MESSAGE_VISIT);
                        messageFrom.setParameterType(Constant.MESSAGE_VISIT_END);
                        messageFrom.setFirstTitle("随访计划結束");
                        messageFrom.setContent("【"+plan.getSolutionName()+"】");
                        messageFrom.setTitle(plan.getPatientName());
                        messageFrom.setParameter(plan.getId().toString());
                        messageFrom.setTeamId(data.getTeamId());
                        messageFrom.setToUserId(plan.getCreateId());
                        messageService.createMessage(messageFrom);
                        return "===================推送消息成功===================";
                    } catch (Exception e) {
                        System.err.println("推送消息遭遇了不测");
                        System.err.println(e.getMessage());
                        return "===================推送消息失败===================";
                    }
                }, executorService);
                messageCreate.thenAccept((e) -> {
                    log.info(e);
                });
                CompletableFuture<String> messageCreate2 = CompletableFuture.supplyAsync(() -> {
                    try {
                        MessageFrom messageFrom = new MessageFrom();
                        messageFrom.setType(Constant.MESSAGE_SYSTEM);
                        messageFrom.setParameterType(Constant.MESSAGE_RECEIVED_SCALE);
                        messageFrom.setFirstTitle("已收到量表");
                        messageFrom.setContent(planNodeItemVo.getName());
                        messageFrom.setTitle(planNodeItemVo.getPatientName());
                        messageFrom.setParameter(planNodeItemVo.getId().toString());
                        messageFrom.setTeamId(data.getTeamId());
                        messageFrom.setToUserId(planNodeItemVo.getDoctorId());
                        messageService.createMessage(messageFrom);
                        return "===================推送消息成功===================";
                    } catch (Exception e) {
                        System.err.println("推送消息遭遇了不测");
                        System.err.println(e.getMessage());
                        return "===================推送消息失败===================";
                    }
                }, executorService);
                messageCreate2.thenAccept((e) -> {
                    log.info(e);
                });
            }
            return true;
        }
    }

    @Override
    public IPage<PlanNodeItemVo> getPage(Page objectPage, Map<String, Object> params) {
        return appPlanNodeItemMapper.getPage(objectPage,params);
    }
}