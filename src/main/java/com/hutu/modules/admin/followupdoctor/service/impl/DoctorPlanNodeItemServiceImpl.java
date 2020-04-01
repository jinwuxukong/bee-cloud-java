package com.hutu.modules.admin.followupdoctor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.admin.followupdoctor.service.DoctorPlanNodeItemService;
import com.hutu.modules.common.entity.Doctor;
import com.hutu.modules.common.entity.PlanNodeItem;
import com.hutu.modules.admin.followupdoctor.mapper.DoctorPlanNodeItemMapper;
import com.hutu.modules.admin.followupdoctor.vo.DoctorFollowupTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DoctorPlanNodeItemServiceImpl extends ServiceImpl<DoctorPlanNodeItemMapper, PlanNodeItem> implements DoctorPlanNodeItemService {
    @Autowired
    private DoctorPlanNodeItemMapper planNodeItemMapper;

    /**
     * @return
     */
    @Override
    public List<DoctorFollowupTaskVo> notCompletedPlanNodeItem() {
        return planNodeItemMapper.notCompletedPlanNodeItem();
    }
}
