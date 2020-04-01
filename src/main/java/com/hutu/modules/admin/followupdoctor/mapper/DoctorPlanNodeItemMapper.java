package com.hutu.modules.admin.followupdoctor.mapper;

import com.hutu.modules.common.entity.PlanNodeItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.modules.admin.followupdoctor.vo.DoctorFollowupTaskVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 随访计划节点子表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-09-28
 */
@Mapper
public interface DoctorPlanNodeItemMapper extends BaseMapper<PlanNodeItem> {

    /**
     * 查找今天未完成的item
     *
     * @return
     */
    List<DoctorFollowupTaskVo> notCompletedPlanNodeItem();

}