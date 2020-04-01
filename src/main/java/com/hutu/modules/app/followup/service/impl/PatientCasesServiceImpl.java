package com.hutu.modules.app.followup.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.followup.entity.PatientCasesVo;
import com.hutu.modules.app.followup.mapper.PatientCasesMapper;
import com.hutu.modules.app.followup.service.PatientCasesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.common.entity.PatientCases;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 患者病历信息 服务实现类
 * </p>
 *
 * @author generator
 * @since 2020-01-09
 */
@Service
public class PatientCasesServiceImpl extends ServiceImpl<PatientCasesMapper, PatientCases> implements PatientCasesService {

    @Autowired
    private PatientCasesMapper mapper;

    @Override
    public List<PatientCases> getList(PatientCases data) {
        return mapper.getList(data);
    }

    @Override
    public IPage<PatientCases> getPage(Page objectPage, Map<String, Object> params) {
        return mapper.getPage(objectPage,params);
    }
}
