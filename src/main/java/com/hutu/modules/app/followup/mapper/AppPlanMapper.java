package com.hutu.modules.app.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.followup.entity.PlanVo;
import com.hutu.modules.common.entity.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


/**
 * <p>
  * 随访计划表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-10-15
 */
@Mapper
public interface AppPlanMapper extends BaseMapper<Plan> {

    IPage<PlanVo> PageVoList(Page objectPage, Map<String, Object> params);

    Plan getPlanByPlanNodeItem(@Param("nodeId") Integer nodeId);
}