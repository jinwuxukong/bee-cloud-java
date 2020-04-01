package com.hutu.modules.app.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.modules.app.followup.entity.AppSolutionNodeItemVo;
import com.hutu.modules.app.followup.entity.SolutionNodeItemFrom;
import com.hutu.modules.common.entity.SolutionNodeItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 方案节点子表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Mapper
public interface AppSolutionNodeItemMapper extends BaseMapper<SolutionNodeItem> {

    List<SolutionNodeItem> getList(@Param("date")SolutionNodeItem date);

    AppSolutionNodeItemVo getOneById(@Param("id") String id);

    List<AppSolutionNodeItemVo> getOneByDate(@Param("from") SolutionNodeItemFrom from);
}