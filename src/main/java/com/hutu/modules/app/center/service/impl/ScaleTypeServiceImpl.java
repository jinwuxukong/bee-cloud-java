package com.hutu.modules.app.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.center.entity.ScaleTypeVo;
import com.hutu.modules.app.center.entity.StatisticsScaleVo;
import com.hutu.modules.app.center.service.ScaleTypeService;
import com.hutu.modules.app.followup.mapper.AppPlanNodeItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 医生信息表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */
@Service
public class ScaleTypeServiceImpl implements ScaleTypeService {

    @Autowired
    private AppPlanNodeItemMapper appPlanNodeItemMapper;

    @Override
    public IPage<ScaleTypeVo> noFinishList(Page<Object> objectPage, Map<String, Object> params) {
        return appPlanNodeItemMapper.noFinishList(objectPage,params);
    }

    @Override
    public IPage<ScaleTypeVo> FinishPage(Page<Object> objectPage, Map<String, Object> params) {
        return appPlanNodeItemMapper.FinishPage(objectPage,params);
    }

    @Override
    public IPage<ScaleTypeVo> overTimePage(Page<Object> objectPage, Map<String, Object> params) {
        return appPlanNodeItemMapper.overTimePage(objectPage,params);
    }

    @Override
    public StatisticsScaleVo statisticsScale(Integer userId) {
        return appPlanNodeItemMapper.statisticsScale(userId);
    }

    @Override
    public StatisticsScaleVo nowStatisticsScale(Integer userId) {
        return appPlanNodeItemMapper.nowStatisticsScale(userId);
    }
}
