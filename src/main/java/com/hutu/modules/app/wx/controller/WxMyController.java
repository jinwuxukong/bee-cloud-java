package com.hutu.modules.app.wx.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.constant.Constant;
import com.hutu.common.entity.R;
import com.hutu.modules.app.message.entity.MessageFrom;
import com.hutu.modules.app.message.service.MessageService;
import com.hutu.modules.app.team.service.TeamMessageService;
import com.hutu.modules.app.team.service.TeamPatientService;
import com.hutu.modules.app.wx.service.WxPatientCasesService;
import com.hutu.modules.app.wx.service.WxPatientService;
import com.hutu.modules.common.entity.Patient;
import com.hutu.modules.common.entity.PatientCases;
import com.hutu.modules.common.entity.TeamPatient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信患者-我的模块
 *
 * @author hutu
 * @date 2019-12-27 18:54
 */
@Api(tags = "微信患者-我的模块")
@RestController
@RequestMapping("wxMy")
public class WxMyController{

    @Autowired
    private WxPatientService patientService;
    @Autowired
    private WxPatientCasesService patientCasesService;
    @Autowired
    private TeamPatientService teamPatientService;
    @Autowired
    private TeamMessageService messageService;

    @AuthIgnore
    @ApiOperation("创建患者信息-保存患者电话与姓名")
    @PostMapping("/create")
    public R create(@RequestBody @ApiParam("数据对象")Patient data){
        if (StrUtil.isEmpty(data.getOpenId())){
            return R.error("openId不能为空");
        }
        List list = patientService.list(new QueryWrapper<Patient>().eq("phone", data.getPhone()));
        if (list.size()!=0){
            return R.error("手机号已注册");
        }
        patientService.update(new UpdateWrapper<Patient>().set("name", data.getName()).set("phone", data.getPhone()).eq("openId", data.getOpenId()));
        Patient patient = patientService.getOne(new QueryWrapper<Patient>().eq("openId", data.getOpenId()));
        if(patient==null){
            return R.error("openid:"+data.getOpenId()+" 查无此患者");
        }
        TeamPatient teamPatient = teamPatientService.getOne(new QueryWrapper<TeamPatient>().eq("patientId", patient.getId()));
        // 发送患者注册消息给责任医师
        MessageFrom messageFrom = new MessageFrom()
                .setItemType(Constant.MESSAGE_ITEM_TYPE_JUMP)
                .setParameterType(Constant.MESSAGE_TEAM_DOCTOR_NEW_PATIENT)
                .setFirstTitle("团队消息")
                .setTitle("新的患者")
                .setContent(patient.getName())
                .setParameter(patient.getId().toString())
                .setTeamId(teamPatient.getTeamId())
                .setToUserId(teamPatient.getDoctorUserId());
        messageService.createMessage(messageFrom);
        return R.ok();
    }

    @AuthIgnore
    @ApiOperation("更新患者信息")
    @PostMapping("/update")
    public R update(@RequestBody @ApiParam("数据对象")Patient data){
        return patientService.updateById(data)?R.ok():R.error("保存错误");
    }

    @AuthIgnore
    @ApiOperation("获取患者个人资料-通过openId")
    @GetMapping("/getByOpenId/{openId}")
    public R read(@ApiParam("患者openId")@PathVariable("openId")String openId){
        return R.ok().put("info",patientService.getOne(new QueryWrapper<Patient>().eq("openId",openId)));
    }

    @AuthIgnore
    @ApiOperation("获取患者个人资料-通过id")
    @GetMapping("/read/{id}")
    public R readById(@ApiParam("患者Id")@PathVariable("id")String id){
        return R.ok().put("info",patientService.getById(id));
    }

    @AuthIgnore
    @ApiOperation("获取患者档案中的所有科室")
    @GetMapping("/getDepartmentList")
    public R getDepartmentList(@ApiParam("患者Id") String patientId) {
        // TODO 科室去重
        List<PatientCases> list = patientCasesService.list(new QueryWrapper<PatientCases>().eq("patientId", patientId));
        ArrayList<String> departmentList = new ArrayList<>();
        list.forEach(obj -> departmentList.add(obj.getDepartmentName()));
        return R.ok().put("info", departmentList);
    }

    @AuthIgnore
    @ApiOperation("通过患者id获取患者病历集合")
    @GetMapping("/getCaseList/{id}")
    public R getCaseList(@ApiParam("数据对象") @PathVariable("id") String id, @ApiParam("就诊科室") @RequestParam(required = false) String departmentName
            , @ApiParam("开始时间") String startTime, @ApiParam("截止时间") String endTime) {

        QueryWrapper<PatientCases> query = new QueryWrapper<PatientCases>().eq(StrUtil.isNotEmpty(departmentName), "departmentName", departmentName)
                .eq("patientId", id).between(StrUtil.isNotEmpty(startTime)&&StrUtil.isNotEmpty(endTime),"visitTime", startTime, endTime);

        return R.ok().put("list", patientCasesService.list(query));
    }

    @AuthIgnore
    @ApiOperation("创建患者病历")
    @PostMapping("/createMedicalRecord")
    public R createMedicalRecord(@RequestBody @ApiParam("数据对象") PatientCases cases){
        return patientCasesService.save(cases)?R.ok():R.error("保存错误");
    }
    @AuthIgnore
    @ApiOperation("通过病历id获取病历信息")
    @GetMapping("/getMedicalRecord/{id}")
    public R getMedicalRecord(@PathVariable("id") @ApiParam("病历id") Integer id){
        return R.ok().put("info",patientCasesService.getById(id));
    }
    @AuthIgnore
    @ApiOperation("更新患者病历")
    @PostMapping("/updateMedicalRecord")
    public R updateMedicalRecord(@RequestBody @ApiParam("数据对象")PatientCases cases){
        return patientCasesService.updateById(cases)?R.ok():R.error("更新错误");
    }
}
