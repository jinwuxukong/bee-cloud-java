package com.hutu.modules.admin.followup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.admin.followup.vo.AdminSolutionVo;
import com.hutu.modules.admin.followup.vo.SolutionFrom;
import com.hutu.modules.common.entity.Solution;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 方案表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
public interface AdminSolutionService extends IService<Solution> {


    R createOrUpdate(Solution data);

    R create(SolutionFrom data);

    SolutionFrom descById(Integer id);

    List<Solution> getList(Solution date);

    IPage<Solution> getPage(Page objectPage, Map<String, Object> params);

    List<Solution> getVersionList(Integer id, String solutionNumber);
}
