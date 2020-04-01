package com.hutu.modules.rest.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hutu.modules.admin.upms.entity.User;
import com.hutu.modules.admin.upms.service.UserService;
import com.hutu.modules.app.center.service.AppDoctorService;
import com.hutu.modules.app.followup.service.PatientService;
import com.hutu.modules.app.team.service.TeamPatientService;
import com.hutu.modules.app.team.service.TeamService;
import com.hutu.modules.common.entity.Doctor;
import com.hutu.modules.common.entity.Patient;
import com.hutu.modules.common.entity.PlanNodeItem;
import com.hutu.modules.common.entity.TeamPatient;
import com.hutu.modules.rest.mapper.JeewxMapper;
import com.hutu.modules.rest.service.JeewxService;
import com.hutu.modules.rest.template.WxTemplateMsg;
import com.hutu.modules.rest.vo.DoctorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * 处理微信相关业务
 * @author hutu
 * @date 2020-01-01 16:10
 */
@Slf4j
@Service("jeewxServiceImpl")
public class JeewxServiceImpl implements JeewxService {

    @Autowired
    JeewxMapper jeewxMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 模板要跳转到的地址
     */
    @Value("${jeewx.followUp.template.goUrl}")
    private String templateGoUrl;
    /**
     * 发送随访表单的接口
     */
    @Value("${jeewx.followUp.template.send}")
    private String sendTemplateUrl;
    /**
     * 模板id
     */
    @Value("${jeewx.followUp.template.templateId1}")
    private String templateId;

    /**
     * 发送微信模板消息
     * @param planNodeItem 随访计划节点信息
     * @return 是否成功
     */
    @Override
    public Boolean sendFormTemplateMessage(PlanNodeItem planNodeItem) {

        DoctorInfo doctorInfo = jeewxMapper.getDoctorInfo(planNodeItem.getDoctorId());
        Patient patient = patientService.getById(planNodeItem.getPatientId());
        // 患者不存在或取关了公众号则直接失败
        if (patient == null || patient.getIsAttention().equals(0)) {
            return false;
        }
        //模板跳转的地址及携带的参数
        String goUrl = templateGoUrl + "?id=" + planNodeItem.getId() + "&teamId=" + planNodeItem.getTeamId();

        Map<String, String> map = new HashMap<>(8);
        // 患者名称
        map.put("first", "您好" + patient.getName() + "，为了您的健康请及时填写量表");
        // 医生名称
        map.put("keyword1", doctorInfo.getName());
        // 医生医院名称
        map.put("keyword2", doctorInfo.getHospitalName());
        // 节点名称
        map.put("keyword3", planNodeItem.getName());
        map.put("remark", "请注意查看");
        WxTemplateMsg templateMsg = new WxTemplateMsg(templateId,patient.getOpenId(),goUrl,map);
        return restTemplate.postForObject(sendTemplateUrl, templateMsg, Boolean.class);
    }

    /**
     * @param openId 取消关注的openId
     * @return
     */
    @Override
    public boolean delBindUnSubscribe(String openId) {
        return patientService.update(new UpdateWrapper<Patient>().eq("openId",openId).set("isAttention",0).set("updateTime", LocalDateTime.now()));
    }

    @Autowired
    TeamPatientService teamPatientService;

    /**
     * @param openId    患者openId
     * @param parameter 二维码参数 param1,param2(param1为医生id，param2为团队id)
     * @return teamName 若为扫码关注，则为团队名称否则为空
     */
    @Override
    public String dealSubEvent(String openId, String parameter) {
        // 判断是否已经有此患者
        Patient patient = patientService.getOne(new QueryWrapper<Patient>().eq("openId", openId));
        // 如果查无患者则新增患者
        if (patient == null) {
            patient = new Patient().setOpenId(openId).setIsAttention(0);
            patientService.save(patient);
        }
        // 若还未关注公众号则修改患者关注状态
        if (patient.getIsAttention().equals(0)){
            patientService.updateById(patient.setIsAttention(1));
        }

        // 如果参数不为null说明为扫码关注
        if (StrUtil.isNotEmpty(parameter)) {
            String[] params = StrUtil.split(parameter, ",");
            if (params != null && params.length == 2) {

                String doctorId = params[0];
                Integer teamId = Integer.parseInt(params[1]);

                // 若患者没有默认关注团队则设置该团队
                if (patient.getDefaultTeamId()==null){
                    patientService.updateById(patient.setDefaultTeamId(teamId));
                }
                User doctor = userService.getById(doctorId);
                TeamPatient teamPatient = teamPatientService.getOne(new QueryWrapper<TeamPatient>().eq("patientId", patient.getId()).eq("teamId", teamId));
                // 患者是否已经关注过该团队
                if (teamPatient == null) {
                    teamPatientService.save(new TeamPatient().setPatientName(patient.getName()).setPatientId(patient.getId()).setDoctorUserId(doctor.getId()).setDoctorName(doctor.getNick())
                            .setTeamId(teamId).setIsBinding(1));
                }
                // 若不是同一个医生则更新
                else if (!doctor.getId().equals(teamPatient.getDoctorUserId())) {
                    teamPatientService.updateById(teamPatient.setDoctorUserId(doctor.getId()).setDoctorName(doctor.getNick()).setIsBinding(1));
                }

                return teamService.getById(teamId).getName();
            }
        }
        return "";
    }

}

