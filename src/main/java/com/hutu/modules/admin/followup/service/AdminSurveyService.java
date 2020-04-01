package com.hutu.modules.admin.followup.service;

import com.hutu.modules.common.entity.Survey;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 表单填写记录表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-07-11
 */
public interface AdminSurveyService extends IService<Survey> {

    boolean saveResult(String data);
}
