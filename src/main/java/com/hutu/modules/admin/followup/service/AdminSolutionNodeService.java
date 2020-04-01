package com.hutu.modules.admin.followup.service;

import com.hutu.common.entity.R;
import com.hutu.modules.common.entity.SolutionNode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 方案节点表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
public interface AdminSolutionNodeService extends IService<SolutionNode> {

    List<SolutionNode> listNodes(Integer id);

    R createOrUpdateSolutionNode(SolutionNode solutionNode);

    int totalRecordMax(Integer solutionId);
}
