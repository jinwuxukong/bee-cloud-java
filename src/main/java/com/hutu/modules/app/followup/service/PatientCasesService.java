package com.hutu.modules.app.followup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.followup.entity.PatientCasesVo;
import com.hutu.modules.common.entity.PatientCases;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 患者病历信息 服务类
 * </p>
 *
 * @author generator
 * @since 2020-01-09
 */
public interface PatientCasesService extends IService<PatientCases> {

    List<PatientCases> getList(PatientCases date);

    IPage<PatientCases> getPage(Page objectPage, Map<String, Object> params);
}
