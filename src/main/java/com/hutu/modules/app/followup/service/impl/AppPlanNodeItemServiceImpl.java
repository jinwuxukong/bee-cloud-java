package com.hutu.modules.app.followup.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.followup.entity.PlanNodeItemVo;
import com.hutu.modules.app.followup.mapper.AppPlanNodeItemMapper;
import com.hutu.modules.app.followup.service.AppPlanNodeItemService;
import com.hutu.modules.common.entity.PlanNodeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 随访计划节点子表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-15
 */
@Service
public class AppPlanNodeItemServiceImpl extends ServiceImpl<AppPlanNodeItemMapper, PlanNodeItem> implements AppPlanNodeItemService {

    @Autowired
    private AppPlanNodeItemMapper appPlanNodeItemMapper;

    @Override
    public int countByStatus(Integer userId, String createTime, int status) {
        return appPlanNodeItemMapper.countByStatus(userId,createTime,status);
    }

    @Override
    public List<PlanNodeItemVo> getList(PlanNodeItem data) {
        return appPlanNodeItemMapper.getList(data);
    }

    @Override
    public PlanNodeItemVo getOneByid(PlanNodeItem data) {
        return appPlanNodeItemMapper.getOneByid(data);
    }
}
