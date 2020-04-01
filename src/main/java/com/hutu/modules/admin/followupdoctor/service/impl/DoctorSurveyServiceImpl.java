package com.hutu.modules.admin.followupdoctor.service.impl;

import com.alibaba.fastjson.JSON;
import com.hutu.modules.admin.followup.vo.KeyValueVo;
import com.hutu.modules.admin.followup.vo.ResultsVo;
import com.hutu.modules.admin.followupdoctor.mapper.DoctorSurveyMapper;
import com.hutu.modules.admin.followupdoctor.service.DoctorResultService;
import com.hutu.modules.admin.followupdoctor.service.DoctorSurveyService;
import com.hutu.modules.common.entity.Result;
import com.hutu.modules.common.entity.Survey;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * <p>
 * 表单填写记录表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-07-11
 */
@Service
public class DoctorSurveyServiceImpl extends ServiceImpl<DoctorSurveyMapper, Survey> implements DoctorSurveyService {
    @Autowired
    private DoctorResultService DoctorResultService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveResult(String data) {
        ResultsVo resultsVo = JSON.parseObject(data, ResultsVo.class);
        Survey survey = new Survey(JSON.parseObject(data).getString("resultList"), JSON.parseObject(data).getString("templateHtml"),resultsVo.getTemplateId());
        save(survey);
        ArrayList<Result> results = new ArrayList<>();
        ArrayList<KeyValueVo> resultList = resultsVo.getResultList();
        resultList.forEach(obj->results.add(new Result(survey.getId(),resultsVo.getTemplateId(),obj.getKey(),obj.getValue())));
        return DoctorResultService.saveBatch(results);
    }
}
