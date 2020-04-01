package com.hutu.modules.app.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.modules.common.entity.PlanNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 随访计划节点表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-10-15
 */
@Mapper
public interface AppPlanNodeMapper extends BaseMapper<PlanNode> {

    int sumDay(@Param("planId") Integer planId);
}