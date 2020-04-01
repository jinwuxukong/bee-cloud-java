package com.hutu.modules.app.followup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.followup.entity.PatientFormVo;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.app.followup.entity.PlanNodeItemVo;
import com.hutu.modules.common.entity.PlanNodeItem;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 直接随访患者表单表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-16
 */
public interface AppPatientFormService extends IService<PlanNodeItem> {

    boolean createOrUpdate(PatientFormVo data) throws Exception;

    List<PlanNodeItem> getByPatientId(String id);

    boolean UpdatePatientForm(PlanNodeItemVo data);

    IPage<PlanNodeItemVo> getPage(Page objectPage, Map<String, Object> params);
}
