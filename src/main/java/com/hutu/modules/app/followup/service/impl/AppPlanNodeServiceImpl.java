package com.hutu.modules.app.followup.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.followup.mapper.AppPlanNodeMapper;
import com.hutu.modules.app.followup.service.AppPlanNodeService;
import com.hutu.modules.common.entity.PlanNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 随访计划节点表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-15
 */
@Service
public class AppPlanNodeServiceImpl extends ServiceImpl<AppPlanNodeMapper, PlanNode> implements AppPlanNodeService {

    @Autowired
    AppPlanNodeMapper appPlanNodeMapper;

    @Override
    public Integer minStatus() {
        return 0;
    }
}
