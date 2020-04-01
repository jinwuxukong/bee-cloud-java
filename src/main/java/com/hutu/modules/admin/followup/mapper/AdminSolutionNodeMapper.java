package com.hutu.modules.admin.followup.mapper;

import com.hutu.modules.common.entity.SolutionNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 方案节点表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Mapper
public interface AdminSolutionNodeMapper extends BaseMapper<SolutionNode> {

    int totalRecordMax(@Param("solutionId") Integer solutionId);
}