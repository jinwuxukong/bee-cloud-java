package com.hutu.modules.app.followup.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.common.constant.Constant;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.followup.entity.*;
import com.hutu.modules.app.followup.mapper.AppPlanMapper;
import com.hutu.modules.app.followup.service.*;
import com.hutu.modules.app.message.entity.MessageFrom;
import com.hutu.modules.app.message.service.MessageService;
import com.hutu.modules.app.team.service.TeamPatientService;
import com.hutu.modules.common.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 随访计划表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-15
 */
@Service
@Slf4j
public class AppPlanServiceImpl extends ServiceImpl<AppPlanMapper, Plan> implements AppPlanService {

    @Autowired
    private PatientService patientService;
    @Autowired
    private AppSolutionService appSolutionService;
    @Autowired
    private AppPlanNodeService appPlanNodeService;
    @Autowired
    private AppPlanNodeItemService appPlanNodeItemService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AppPlanMapper planMapper;
    @Autowired
    private TeamPatientService teamPatientService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R create(PlanVo data) {
        Integer userId = JwtUtils.getUserId();
        if(data.getIds().length<1){
            return R.error("请添加患者");
        }else{
            for (Integer id : data.getIds()) {
                //查询患者详情
                PatientVo patientVo = patientService.read(id, userId);
                Integer solutionId = data.getSolutionId();
                //查询方案详情
                SolutionVo solutionVo = appSolutionService.readById(solutionId);
                data.setScope(solutionVo.getScope());
                data.setScopeShow(solutionVo.getScopeShow());
                data.setIntroduce(solutionVo.getIntroduce());
                data.setTeamId(data.getTeamId());
                data.setSolutionId(solutionId);
                data.setPatientId(id);
                //修改患者是否有计划状态
                teamPatientService.update(new UpdateWrapper<TeamPatient>().set("isPlan",1).eq("patientId",id).eq("doctorUserId",userId));
                data.setPatientName(patientVo.getName());
                data.setSolutionName(solutionVo.getName());
                data.setStatus(0);
                data.setEndTime(data.getStartTime().plusDays(solutionVo.getPredictDay()));
                Plan plan = new Plan();
                BeanUtils.copyProperties(data,plan);
                plan.setExecutorId(JwtUtils.getUserId());
                plan.setExecutorName(JwtUtils.getUserName());
                plan.setVisitCourse("1/"+solutionVo.getVisitNum().toString());
                plan.setVisitNum(solutionVo.getVisitNum());
                plan.setPredictDay(solutionVo.getPredictDay());
                this.save(plan);
                data.setId(plan.getId());
                MessageFrom messageFrom = new MessageFrom();
                messageFrom.setType(Constant.MESSAGE_VISIT);
                messageFrom.setParameterType(Constant.MESSAGE_VISIT_START);
                messageFrom.setFirstTitle("随访计划开始");
                messageFrom.setContent(plan.getName() + ":" + solutionVo.getName());
                messageFrom.setTitle(patientVo.getName());
                messageFrom.setParameter(data.getId().toString());
                messageFrom.setToUserId(userId);
                messageService.createMessage(messageFrom);
                List<SolutionNodeVo> solutionNodeVoLists = solutionVo.getSolutionNodeVoLists();
                List<PlanNode> planNodeList = new ArrayList<>();
                List<PlanNodeItem> planNodeItemList = new ArrayList<>();
                for (SolutionNodeVo solutionNodeVoList : solutionNodeVoLists) {
                    PlanNode planNode = new PlanNode();
                    planNode.setFollowupPlanId(data.getId());
                    planNode.setName(solutionNodeVoList.getName());
                    planNode.setIntervalDay(solutionNodeVoList.getIntervalDay());
                    planNode.setExecuteDate(data.getStartTime().plusDays(solutionNodeVoList.getIntervalValue().longValue()));
                    planNode.setOrders(solutionNodeVoList.getOrders());
                    planNode.setStatus(0);
                    planNodeList.add(planNode);
                }
                appPlanNodeService.saveBatch(planNodeList);
                for (int i = 0; i < solutionNodeVoLists.size(); i++) {
                    SolutionNodeVo solutionNodeVoList = solutionNodeVoLists.get(i);
                    if(solutionNodeVoList.getFormSolutionNodeItemList().size()>0){
                        for (AppSolutionNodeItemVo appSolutionNodeItemVo : solutionNodeVoList.getFormSolutionNodeItemList()) {
                            solutionNodeItemToPlanNodeItem(planNodeList.get(i).getId(),data, userId, id, patientVo, planNodeItemList, solutionNodeVoList, appSolutionNodeItemVo,planNodeList.get(i).getExecuteDate());
                        }
                    }
                    if(solutionNodeVoList.getVisitSolutionNodeItemList().size()>0){
                        for (AppSolutionNodeItemVo appSolutionNodeItemVo : solutionNodeVoList.getVisitSolutionNodeItemList()) {
                            solutionNodeItemToPlanNodeItem(planNodeList.get(i).getId(),data, userId, id, patientVo, planNodeItemList, solutionNodeVoList, appSolutionNodeItemVo,planNodeList.get(i).getExecuteDate());
                        }
                    }
                }
                appPlanNodeItemService.saveBatch(planNodeItemList);
            }
        }
        return R.ok();
    }

    private void solutionNodeItemToPlanNodeItem(Integer planNodeId, PlanVo data, Integer userId, Integer id, PatientVo patientVo, List<PlanNodeItem> planNodeItemList, SolutionNodeVo solutionNodeVoList, AppSolutionNodeItemVo appSolutionNodeItemVo, LocalDate executeDate) {
        PlanNodeItem planNodeItem = new PlanNodeItem();
        planNodeItem.setNodeId(planNodeId);
        planNodeItem.setStatus(0);
        planNodeItem.setName(appSolutionNodeItemVo.getTemplateName());
        planNodeItem.setDoctorId(userId);
        planNodeItem.setTeamId(data.getTeamId());
        planNodeItem.setScaleType(0);
        planNodeItem.setScaleTypeShow("计划随访");
        planNodeItem.setPatientId(id);
        planNodeItem.setPatientName(patientVo.getName());
        planNodeItem.setPatientPhoto(patientVo.getAvatar());
        planNodeItem.setPhone(patientVo.getPhone());
        planNodeItem.setTemplateId(appSolutionNodeItemVo.getTemplateId());
        planNodeItem.setCreateTime(LocalDateTime.of(executeDate,LocalTime.now()));
        planNodeItemList.add(planNodeItem);
    }

    @Override
    public R readByPatient(Integer id) {
        Plan plan = this.getOne(new QueryWrapper<Plan>().eq("id",id));
        /*Patient byId = appPatientService.getById(plan.getPatientId());
        if(byId==null){
            return R.error("该患者已删除");
        }*/
        PlanVo planVo = new PlanVo();
        if(plan!=null){
            planVo = PlanToPlanVo(plan);
        }
        return R.ok().put("info", planVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePlan(List<String> split) {
        for (String id:split) {
            this.removeById(id);
            List<PlanNode> followupPlanId = appPlanNodeService.list(new QueryWrapper<PlanNode>().eq("followupPlanId", id));
            for (PlanNode planNode : followupPlanId) {
                List<PlanNodeItem> nodeId = appPlanNodeItemService.list(new QueryWrapper<PlanNodeItem>().eq("nodeId", planNode.getId()));
                for (PlanNodeItem planNodeItem : nodeId) {
                    appPlanNodeItemService.removeById(planNodeItem.getId());
                }
            }
        }
        return true;
    }

    @Override
    public List<PlanVo> toPlanVoList(List<Plan> records) {
        List<PlanVo> planVos = new ArrayList<>();
        for (Plan record : records) {
            PlanVo planVo = new PlanVo();
            BeanUtils.copyProperties(record,planVo);
            int followupPlanId = appPlanNodeService.count(new QueryWrapper<PlanNode>().eq("followupPlanId", record.getId()));
            appPlanNodeService.minStatus();
            planVo.setDayNum(record.getPredictDay());
            planVo.setVisitNum(followupPlanId);
            planVos.add(planVo);
        }
        return planVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean terminationPlan(String ids) {
        this.update(new UpdateWrapper<Plan>().set("status",3).in("id", StrUtil.split(ids,',')));
        List<Plan> plans = (List<Plan>)this.listByIds(StrUtil.split(ids, ','));
        for (Plan plan : plans) {
            appPlanNodeService.update(new UpdateWrapper<PlanNode>().set("status",3).eq("followupPlanId",plan.getId()));
            List<PlanNode> planNodeList = appPlanNodeService.list(new QueryWrapper<PlanNode>().eq("followupPlanId", plan.getId()));
            for (PlanNode planNode : planNodeList) {
                List<PlanNodeItem> planNodeItem = appPlanNodeItemService.list(new QueryWrapper<PlanNodeItem>().eq("nodeId", planNode.getId()));
                for (PlanNodeItem nodeItem : planNodeItem) {
                    if(nodeItem.getStatus()!=1){
                        appPlanNodeItemService.update(new UpdateWrapper<PlanNodeItem>().set("status",3).eq("id",nodeItem.getId()));
                    }
                }
            }
        }
        return true;
    }

    @Override
    public IPage<PlanVo> PageVoList(Page objectPage, Map<String, Object> params) {
        return planMapper.PageVoList(objectPage,params);
    }

    @Override
    public PlanVo getOnePlan(Plan date) {
        return PlanToPlanVo(date);
    }

    @Override
    public Plan getPlanByPlanNodeItem(Integer nodeId) {
        return planMapper.getPlanByPlanNodeItem(nodeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changeDoctor(Integer patientId, Integer doctorOldId, Integer doctorNowId, String doctorName, Integer teamId) {
         ExecutorService executor= Executors.newFixedThreadPool(1);
         CompletableFuture<Boolean> future = CompletableFuture.supplyAsync(()-> {
                 try {
                     List<PlanNodeItem> planNodeItemList = appPlanNodeItemService.list(new QueryWrapper<PlanNodeItem>().eq("doctorId", doctorOldId).eq("teamId", teamId).eq("patientId", patientId).eq("createId", doctorOldId));
                     List<Integer> ids = new ArrayList<>();
                     if(planNodeItemList.size()>0){
                         //临时随访更换医生信息并保存并删除旧数据
                         for (PlanNodeItem planNodeItem : planNodeItemList) {
                             ids.add(planNodeItem.getId());
                             planNodeItem.setId(null);
                             planNodeItem.setDoctorId(doctorNowId);
                             planNodeItem.setCreateId(doctorNowId);
                             planNodeItem.setCreateName(doctorName);
                         }
                         appPlanNodeItemService.removeByIds(ids);
                         appPlanNodeItemService.saveBatch(planNodeItemList);
                     }
                     List<Plan> planList = this.list(new QueryWrapper<Plan>().eq("patientId", patientId).eq("createId",doctorOldId));
                     List<Plan> planOld = new ArrayList<>();
                     planOld.addAll(planList);
                     //获取患者所有计划并逻辑删除以前的计划并修改医生相关参数在保存
                     if(planList.size()>0){
                         ids.clear();
                         //遍历计划更改医生参数并保存
                         for (int i = 0; i < planList.size(); i++) {
                             ids.add(planList.get(i).getId());
                             planList.get(i).setCreateId(doctorNowId);
                             planList.get(i).setId(null);
                         }
                         this.removeByIds(ids);
                         this.saveBatch(planList);
                         //遍历旧计划查询旧计划节点更改医生参数并保存
                         for (Plan plan : planOld) {
                             List<PlanNode> planNode = appPlanNodeService.list(new QueryWrapper<PlanNode>().eq("followupPlanId", plan.getId()));
                             List<PlanNode> planNodeOld = new ArrayList<>();
                             planNode.addAll(planNode);
                             if(planNode.size()>0){
                                 ids.clear();
                                 //遍历计划节点修改医生参数
                                 for (int i = 0; i < planNode.size(); i++) {
                                     PlanNode node =  planNode.get(i);
                                     ids.add(node.getId());
                                     planNode.get(i).setFollowupPlanId(plan.getId());
                                     planList.get(i).setId(null);
                                 }
                                 appPlanNodeService.removeByIds(ids);
                                 appPlanNodeService.saveBatch(planNode);
                                 //遍历旧计划节点查询旧计划节点子节点更改医生参数并保存
                                 for (PlanNode node : planNodeOld) {
                                     List<PlanNodeItem> planNodeItem = appPlanNodeItemService.list(new QueryWrapper<PlanNodeItem>().eq("nodeId", node.getId()));
                                     if(planNodeItem.size()>0){
                                         ids.clear();
                                         //遍历计划节点子节点修改医生参数
                                         for (int i = 0; i < planNodeItem.size(); i++) {
                                             PlanNodeItem nodeItem =  planNodeItem.get(i);
                                             ids.add(nodeItem.getId());
                                             nodeItem.setDoctorId(doctorNowId);
                                             nodeItem.setCreateId(doctorNowId);
                                             nodeItem.setCreateName(doctorName);
                                             nodeItem.setId(null);
                                         }
                                         appPlanNodeItemService.removeByIds(ids);
                                         appPlanNodeItemService.saveBatch(planNodeItem);
                                     }
                                 }
                             }
                         }
                     }
                     log.info("患者更换医生执行完成");
                     return true;
                     } catch (Exception e) {
                        log.info("患者更换医生的程序遭遇了不测");
                        return false;
                     }
             },executor);
//        future.thenAccept((e)->{System.out.println("执行完毕："+e);});
        return true;
    }

    private PlanVo PlanToPlanVo(Plan plan){
        PlanVo planVo = new PlanVo();
        BeanUtils.copyProperties(plan,planVo);
        List<PlanNode> planNodeList = appPlanNodeService.list(new QueryWrapper<PlanNode>().eq("followupPlanId", planVo.getId()));
        List<PlanNodeVo> planNodeVos = new ArrayList<>();
        for (PlanNode planNode : planNodeList) {
            PlanNodeVo planNodeVo = new PlanNodeVo();
            BeanUtils.copyProperties(planNode,planNodeVo);
            List<PlanNodeItemVo> nodeId = appPlanNodeItemService.getList(new PlanNodeItem().setNodeId(planNodeVo.getId()));
            List<PlanNodeItemVo> FormPlanNodeItemList = new ArrayList<>();
            List<PlanNodeItemVo> VisitPlanNodeItemList = new ArrayList<>();
            for (PlanNodeItemVo planNodeItem : nodeId) {
                if(planNodeItem.getType()==1){
                    PlanNodeItemVo planNodeItemVo = new PlanNodeItemVo();
                    BeanUtils.copyProperties(planNodeItem,planNodeItemVo);
                    FormPlanNodeItemList.add(planNodeItemVo);
                }else{
                    PlanNodeItemVo planNodeItemVo = new PlanNodeItemVo();
                    BeanUtils.copyProperties(planNodeItem,planNodeItemVo);
                    VisitPlanNodeItemList.add(planNodeItemVo);
                }
            }
            planNodeVo.setVisitPlanNodeItemList(VisitPlanNodeItemList);
            planNodeVo.setFormPlanNodeItemList(FormPlanNodeItemList);
            planNodeVos.add(planNodeVo);
        }
        planVo.setDayNum(plan.getPredictDay());
        planVo.setPlanNodeVo(planNodeVos);
        return planVo;
    }
}
