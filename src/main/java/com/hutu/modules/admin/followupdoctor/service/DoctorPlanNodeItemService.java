package com.hutu.modules.admin.followupdoctor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.common.entity.PlanNodeItem;
import com.hutu.modules.admin.followupdoctor.vo.DoctorFollowupTaskVo;

import java.util.List;

/**
 * <p>
 * 随访计划节点子表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-09-28
 */
public interface DoctorPlanNodeItemService extends IService<PlanNodeItem> {

    List<DoctorFollowupTaskVo> notCompletedPlanNodeItem();

}
