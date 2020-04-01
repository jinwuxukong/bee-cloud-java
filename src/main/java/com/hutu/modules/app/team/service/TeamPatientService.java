package com.hutu.modules.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.common.entity.R;
import com.hutu.modules.app.team.entity.TeamPatientVo;
import com.hutu.modules.common.entity.TeamPatient;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生团队患者关系中间表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-12-25
 */
public interface TeamPatientService extends IService<TeamPatient> {

    List<TeamPatient> getTeamPatient(TeamPatient teamPatient);

    IPage<TeamPatientVo> getPage(Page objectPage, Map<String, Object> params);

    R changeDoctor(Integer patientId, Integer doctorId, String doctorName, Integer teamId);
}
