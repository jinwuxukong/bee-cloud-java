package com.hutu.modules.app.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.modules.common.entity.SolutionNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 方案节点表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Mapper
public interface AppSolutionNodeMapper extends BaseMapper<SolutionNode> {

    List<SolutionNode> getList(@Param("date")SolutionNode date);
}