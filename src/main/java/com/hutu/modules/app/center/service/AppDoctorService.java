package com.hutu.modules.app.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.common.entity.Doctor;

import java.util.Map;

/**
 * <p>
 * 医生信息表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */
public interface AppDoctorService extends IService<Doctor> {

    /**
     * 随访的医生登录
     *
     * @param data
     * @return
     */
//    boolean register(DoctorRegisterForm data);

    PatientVo WxRead(String openId);

    IPage<TeamMemberVo> getPage(Page objectPage, Map<String, Object> params);

    /**
     * 修改默认团队
     * @param userId
     * @param TeamId
     * @param TeamName
     * @return
     */
    Boolean setDefaultTeam(Integer userId,Integer TeamId,String TeamName);
}
