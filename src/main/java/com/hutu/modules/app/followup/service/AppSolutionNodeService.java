package com.hutu.modules.app.followup.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.common.entity.R;
import com.hutu.modules.common.entity.SolutionNode;

import java.util.List;

/**
 * <p>
 * 方案节点表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
public interface AppSolutionNodeService extends IService<SolutionNode> {

    List<SolutionNode> listNodes(Integer id);

    R createOrUpdateSolutionNode(SolutionNode solutionNode);

    List<SolutionNode> getList(SolutionNode date);
}
