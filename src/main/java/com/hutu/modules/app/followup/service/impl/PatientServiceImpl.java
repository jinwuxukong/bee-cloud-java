package com.hutu.modules.app.followup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.admin.upms.service.UserService;
import com.hutu.modules.app.center.entity.StatisticsPatientVo;
import com.hutu.modules.app.followup.entity.PatientFrom;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.app.followup.mapper.PatientMapper;
import com.hutu.modules.app.followup.service.AppPatientRefService;
import com.hutu.modules.app.followup.service.AppPlanNodeItemService;
import com.hutu.modules.app.followup.service.AppPlanService;
import com.hutu.modules.app.followup.service.PatientService;
import com.hutu.modules.app.message.service.MessageItemService;
import com.hutu.modules.app.message.service.MessageService;
import com.hutu.modules.common.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生患者信息表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-14
 */
@Service
@SuppressWarnings(value = "all")
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {

    @Autowired
    private UserService userService;
    @Autowired
    private AppPlanService appPlanService;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private AppPatientRefService appPatientRefService;
    @Autowired
    private PatientMapper appPatientMapper;
    @Autowired
    private MessageItemService messageItemService;
    @Autowired
    private AppPlanNodeItemService appPlanNodeItemService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PatientMapper mapper;


    @Override
    public List<Plan> readPlan(String id) {
        List<Plan> list = appPlanService.list(new QueryWrapper<Plan>().eq("patientId", id).eq("createId", JwtUtils.getUserId()));
        return list;
    }

    @Override
    public List<StatisticsPatientVo> statisticsPatient(Integer userId, int dayNum) {
        List<StatisticsPatientVo> statisticsPatientVo = appPatientMapper.statisticsPatient(userId, dayNum);
        return statisticsPatientVo;
    }

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public boolean Delete(List<String> split) throws Exception {
        Patient doctorPatient = this.getById(split.get(0));
        Integer doctorUserId = doctorPatient.getDoctorUserId();
        StringBuffer plans = new StringBuffer();
        StringBuffer patients = new StringBuffer();
        boolean delete = true;
        //删除患者
        if(split!=null && split.size()>0){
            delete = this.removeByIds(split);
        }else{
            return false;
        }
        for (String str : split) {
            List<Plan> planList = appPlanService.list(new QueryWrapper<Plan>().eq("patientId", str));
            if(planList!=null && planList.size()>0){
                for (Plan plan : planList) {
                    plans.append(plan.getId());
                }
            }
            patients.append(str);
        }
        //查询已完成量表
        List<PlanNodeItem> list1 = appPlanNodeItemService.list(new QueryWrapper<PlanNodeItem>().eq("isWeChatRead", 1).eq("isSendWeChat", 1).in("patientId", StrUtil.split(patients, ',')));
        //关联删除临时随访
        List<PlanNodeItem> planNodeItem = appPlanNodeItemService.list(new QueryWrapper<PlanNodeItem>().in("patientId", StrUtil.split(patients, ',')));
        List<String> ids = new ArrayList<>();
        if(planNodeItem!=null && planNodeItem.size()>0){
            for (PlanNodeItem nodeItem : planNodeItem) {
                ids.add(nodeItem.getId().toString());
            }
        }
        boolean deleteItem = true;
        if(ids!=null && ids.size()>0){
            //关联删除计划随访
            deleteItem = appPlanNodeItemService.removeByIds(ids);
        }
        boolean deletePlan = true;
        if(StrUtil.isNotEmpty(plans)){
            //关联删除计划
            deletePlan = appPlanService.deletePlan(StrUtil.split(plans, ','));
        }
        //关联删除随访申请下的患者消息
        boolean remove = messageItemService.remove(new QueryWrapper<MessageItem>().eq("type", 0).eq("parameterType", 3).in("parameter", StrUtil.split(patients, ',')));
        List<MessageItem> list = messageItemService.list(new QueryWrapper<MessageItem>().eq("type", 0).eq("parameterType", 3).eq("toUserId", doctorUserId).orderByDesc("createTime"));
        boolean update = true;
        boolean remove1 = true;
        if(list!=null && list.size()>0){
            Message one = messageService.getOne(new QueryWrapper<Message>().eq("createId", doctorUserId).eq("type", 0).eq("parameterType", 3));
            update = messageService.update(new UpdateWrapper<Message>().set("statusNum", one.getStatusNum()!=0?one.getStatusNum()-StrUtil.split(patients, ',').size():0).set("content", list.get(0).getTitle() + " " + list.get(0).getContent()).eq("createId", doctorUserId).eq("type", 0).eq("parameterType", 3));
        }else{
            remove1 = messageService.remove(new QueryWrapper<Message>().eq("createId", doctorUserId).eq("type", 0).eq("parameterType", 3));
        }
        //关联删除消息已收到量表
        ArrayList<Integer> patientIds = new ArrayList<>();
        boolean removeMessageScale = true;
        boolean updateMessage = true;
        boolean removeMessage = true;
        if(list1!=null && list1.size()>0){
            for (PlanNodeItem nodeItem : list1) {
                patientIds.add(nodeItem.getId());
            }
            removeMessageScale = messageItemService.remove(new QueryWrapper<MessageItem>().eq("type", 0).eq("parameterType", 4).in("parameter", patientIds));
            List<MessageItem> MessageItem = messageItemService.list(new QueryWrapper<MessageItem>().eq("type", 0).eq("parameterType", 4).eq("toUserId", doctorUserId).orderByDesc("createTime"));
            if(MessageItem!=null && MessageItem.size()>0){
                Message one = messageService.getOne(new QueryWrapper<Message>().eq("createId", doctorUserId).eq("type", 0).eq("parameterType", 4));
                updateMessage = messageService.update(new UpdateWrapper<Message>().set("statusNum", one.getStatusNum()!=0?one.getStatusNum()-StrUtil.split(patients, ',').size():0).set("content", MessageItem.get(0).getTitle() + " " + MessageItem.get(0).getContent()).eq("createId", doctorUserId).eq("type", 0).eq("parameterType", 4));
            }else{
                removeMessage = messageService.remove(new QueryWrapper<Message>().eq("createId", doctorUserId).eq("type", 0).eq("parameterType", 4));
            }
        }
        //关联删除信息计划开始
        if(StrUtil.isNotEmpty(plans) && StrUtil.split(plans, ',').size()>0){
            boolean removeMessagePlan = messageItemService.remove(new QueryWrapper<MessageItem>().eq("type", 1).eq("parameterType", 1).in("parameter", StrUtil.split(plans, ',')));
            List<MessageItem> MessageItemPlan = messageItemService.list(new QueryWrapper<MessageItem>().eq("type", 1).eq("parameterType", 1).eq("toUserId", doctorUserId).orderByDesc("createTime"));
            if(MessageItemPlan!=null && MessageItemPlan.size()>0){
                Message one = messageService.getOne(new QueryWrapper<Message>().eq("createId", doctorUserId).eq("type", 1).eq("parameterType", 1));
                boolean updatePlanMessage = messageService.update(new UpdateWrapper<Message>().set("statusNum", one.getStatusNum()!=0?one.getStatusNum()-StrUtil.split(patients, ',').size():0).set("content", MessageItemPlan.get(0).getTitle() + " " + MessageItemPlan.get(0).getContent()).eq("createId", doctorUserId).eq("type", 1).eq("parameterType", 1));
            }else{
                boolean removePlanMessage = messageService.remove(new QueryWrapper<Message>().eq("createId", doctorUserId).eq("type", 1).eq("parameterType", 1));
            }
        }
        //关联删除信息计划结束
        if(StrUtil.isNotEmpty(plans) && StrUtil.split(plans, ',').size()>0){
            boolean removeMessageEndPlan = messageItemService.remove(new QueryWrapper<MessageItem>().eq("type", 1).eq("parameterType", 2).in("parameter", StrUtil.split(plans, ',')));
            List<MessageItem> MessageItemPlan = messageItemService.list(new QueryWrapper<MessageItem>().eq("type", 1).eq("parameterType", 2).eq("toUserId", doctorUserId).orderByDesc("createTime"));
            if(MessageItemPlan!=null && MessageItemPlan.size()>0){
                Message one = messageService.getOne(new QueryWrapper<Message>().eq("createId", doctorUserId).eq("type", 1).eq("parameterType", 2));
                boolean updatePlanEndMessage = messageService.update(new UpdateWrapper<Message>().set("statusNum", one.getStatusNum()!=0?one.getStatusNum()-StrUtil.split(patients, ',').size():0).set("content", MessageItemPlan.get(0).getTitle() + " " + MessageItemPlan.get(0).getContent()).eq("createId", doctorUserId).eq("type", 1).eq("parameterType", 2));
            }else{
                boolean removePlanEndMessage = messageService.remove(new QueryWrapper<Message>().eq("createId", doctorUserId).eq("type", 1).eq("parameterType", 2));
            }
        }
        if (delete && deletePlan && deleteItem) {
            return true;
        } else {
            throw new Exception("患者删除失败，请稍后再试");
        }
    }*/

    /*@Override
    public boolean UpdateBindStatus(PatientFormVo data) {
        UpdateWrapper<Patient> doctorPatientUpdateWrapper = new UpdateWrapper<>();
        doctorPatientUpdateWrapper.set("bindStatus", data.getBindStatus()).eq("id", data.getId());
        messageItemService.update(new UpdateWrapper<MessageItem>().set("isAgree", 1).eq("id", data.getMessageItemId()));
        return this.update(doctorPatientUpdateWrapper);
    }*/

    @Override
    public IPage<PatientVo> pagePatient(Page page, Map<String, Object> params) {
        return patientMapper.pagePatient(page,params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createOrUpdate(PatientFrom data, List<PatientRef> patientRef) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(data,patient);
        this.saveOrUpdate(patient);
        if(patientRef!=null){
            appPatientRefService.saveOrUpdateBatch(patientRef);
        }else{
            patientRef.get(0).setPatientId(patient.getId());
            patientRef.get(1).setPatientId(patient.getId());
            appPatientRefService.saveOrUpdateBatch(patientRef);
        }
        return R.ok();
    }

    @Override
    public PatientVo read(Integer id, Integer userId) {
        return patientMapper.read(id,userId);
    }

    @Override
    public List<Patient> getList(Patient data) {
        return mapper.getList(data);
    }
}
