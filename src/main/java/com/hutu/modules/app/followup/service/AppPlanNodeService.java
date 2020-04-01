package com.hutu.modules.app.followup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.common.entity.PlanNode;

/**
 * <p>
 * 随访计划节点表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-15
 */
public interface AppPlanNodeService extends IService<PlanNode> {

    Integer minStatus();
}
