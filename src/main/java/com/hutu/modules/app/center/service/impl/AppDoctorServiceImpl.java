package com.hutu.modules.app.center.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.message.service.MessageService;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.app.center.mapper.AppDoctorMapper;
import com.hutu.modules.app.center.service.AppDoctorService;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.common.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 医生信息表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */
@Service
public class AppDoctorServiceImpl extends ServiceImpl<AppDoctorMapper, Doctor> implements AppDoctorService {

    @Autowired
    private AppDoctorMapper appDoctorMapper;
    @Autowired
    private MessageService MessageService;

    @Override
    public PatientVo WxRead(String openId) {
        return appDoctorMapper.WxRead(openId);
    }

    @Override
    public IPage<TeamMemberVo> getPage(Page page, Map<String, Object> params) {
        return appDoctorMapper.getPage(page,params);
    }

    @Override
    public Boolean setDefaultTeam(Integer userId, Integer TeamId, String TeamName) {
        return this.update(new UpdateWrapper<Doctor>().set("doctorTeamId", TeamId).set("doctorTeam", TeamName).eq("userId", userId));
    }
}
