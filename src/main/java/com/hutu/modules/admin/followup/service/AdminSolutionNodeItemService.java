package com.hutu.modules.admin.followup.service;

import com.hutu.common.entity.R;
import com.hutu.modules.admin.followup.vo.AdminSolutionNodeItemFrom;
import com.hutu.modules.admin.followup.vo.AdminSolutionNodeItemVo;
import com.hutu.modules.common.entity.SolutionNodeItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * <p>
 * 方案节点子表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
public interface AdminSolutionNodeItemService extends IService<SolutionNodeItem> {

    R saveOrUpdateContainsTemplate(SolutionNodeItem data);

    List<AdminSolutionNodeItemVo> getOneByDate(AdminSolutionNodeItemFrom setSolutionNodeId);
}
