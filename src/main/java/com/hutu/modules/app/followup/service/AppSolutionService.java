package com.hutu.modules.app.followup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.common.entity.R;
import com.hutu.modules.app.followup.entity.SolutionVo;
import com.hutu.modules.common.entity.Solution;

import java.util.List;

/**
 * <p>
 * 方案表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
public interface AppSolutionService extends IService<Solution> {

    List<Solution> getList(Solution date);

    R createOrUpdate(Solution data);

    SolutionVo readById(Integer id);

    Integer countStatistics(Integer userId, int scope);
}
