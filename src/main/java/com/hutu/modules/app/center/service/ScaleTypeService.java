package com.hutu.modules.app.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.center.entity.ScaleTypeVo;
import com.hutu.modules.app.center.entity.StatisticsScaleVo;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.common.entity.PatientForm;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 量表状态查询 服务类
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */
public interface ScaleTypeService {


    IPage<ScaleTypeVo> noFinishList(Page<Object> objectPage, Map<String, Object> params);

    IPage<ScaleTypeVo> FinishPage(Page<Object> objectPage, Map<String, Object> params);

    IPage<ScaleTypeVo> overTimePage(Page<Object> objectPage, Map<String, Object> params);

    StatisticsScaleVo statisticsScale(Integer userId);

    StatisticsScaleVo nowStatisticsScale(Integer userId);
}
