package com.hutu.modules.app.followup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.common.entity.R;
import com.hutu.modules.app.followup.entity.PlanVo;
import com.hutu.modules.common.entity.Plan;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 随访计划表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-15
 */
public interface AppPlanService extends IService<Plan> {

    R create(PlanVo data);

    R readByPatient(Integer id);

    boolean deletePlan(List<String> split);

    List<PlanVo> toPlanVoList(List<Plan> records);

    boolean terminationPlan(String ids);

    IPage<PlanVo> PageVoList(Page objectPage, Map<String, Object> params);

    PlanVo getOnePlan(Plan date);

    Plan getPlanByPlanNodeItem(Integer nodeId);

    boolean changeDoctor(Integer patientId, Integer doctorOldId, Integer doctorNowId, String doctorName, Integer teamId);
}
