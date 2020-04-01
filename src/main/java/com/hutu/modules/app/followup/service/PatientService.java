package com.hutu.modules.app.followup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.common.entity.R;
import com.hutu.modules.app.center.entity.StatisticsPatientVo;
import com.hutu.modules.app.followup.entity.PatientFrom;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.common.entity.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生患者信息表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-14
 */
public interface PatientService extends IService<Patient> {


    List<Plan> readPlan(String id);

    List<StatisticsPatientVo> statisticsPatient(Integer userId, int dayNum);


//    boolean Delete(List<String> split) throws Exception;

//    boolean UpdateBindStatus(PatientFormVo data);

    IPage<PatientVo> pagePatient(Page page, Map<String, Object> params);

    R createOrUpdate(PatientFrom data, List<PatientRef> patientRef);

    PatientVo read(Integer id, Integer userId);

    List<Patient> getList(Patient patient);
}
