package com.hutu.modules.app.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.center.entity.ScaleTypeVo;
import com.hutu.modules.app.center.entity.StatisticsScaleVo;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.app.followup.entity.PlanNodeItemVo;
import com.hutu.modules.common.entity.PlanNodeItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 随访计划节点子表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-10-15
 */
@Mapper
public interface AppPlanNodeItemMapper extends BaseMapper<PlanNodeItem> {

    Integer countByStatus(@Param("userId") Integer userId, @Param("createTime") String createTime, @Param("status") int status);

    List<PlanNodeItemVo> getList(@Param("date")PlanNodeItem date);

    PlanNodeItemVo getOneByid(@Param("date") PlanNodeItem data);

    IPage<ScaleTypeVo> noFinishList(Page page, Map<String, Object> params);

    IPage<ScaleTypeVo> FinishPage(Page page, Map<String, Object> params);

    IPage<ScaleTypeVo> overTimePage(Page page, Map<String, Object> params);

    StatisticsScaleVo statisticsScale(@Param("userId")Integer userId);

    StatisticsScaleVo nowStatisticsScale(@Param("userId") Integer userId);

    IPage<PlanNodeItemVo> getPage(Page objectPage, Map<String, Object> params);
}