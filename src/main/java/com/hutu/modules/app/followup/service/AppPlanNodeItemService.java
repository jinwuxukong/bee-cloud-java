package com.hutu.modules.app.followup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.followup.entity.PlanNodeItemVo;
import com.hutu.modules.common.entity.PlanNodeItem;

import java.util.List;

/**
 * <p>
 * 随访计划节点子表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-15
 */
public interface AppPlanNodeItemService extends IService<PlanNodeItem> {

    int countByStatus(Integer userId, String createTime, int status);

    List<PlanNodeItemVo> getList(PlanNodeItem data);

    PlanNodeItemVo getOneByid(PlanNodeItem data);
}
