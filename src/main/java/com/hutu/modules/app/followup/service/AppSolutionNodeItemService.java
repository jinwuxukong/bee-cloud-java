package com.hutu.modules.app.followup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.followup.entity.AppSolutionNodeItemVo;
import com.hutu.modules.app.followup.entity.SolutionNodeItemFrom;
import com.hutu.modules.common.entity.SolutionNodeItem;

import java.util.List;

/**
 * <p>
 * 方案节点子表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
public interface AppSolutionNodeItemService extends IService<SolutionNodeItem> {

    List<SolutionNodeItem> getList(SolutionNodeItem date);

    AppSolutionNodeItemVo getOneById(String id);

    List<AppSolutionNodeItemVo> getOneByDate(SolutionNodeItemFrom from);
}
