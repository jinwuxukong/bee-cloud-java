package com.hutu.modules.admin.followup.mapper;

import com.hutu.modules.admin.followup.vo.AdminSolutionNodeItemFrom;
import com.hutu.modules.admin.followup.vo.AdminSolutionNodeItemVo;
import com.hutu.modules.common.entity.SolutionNodeItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface AdminSolutionNodeItemMapper extends BaseMapper<SolutionNodeItem> {

    List<AdminSolutionNodeItemVo> getOneByDate(@Param("from") AdminSolutionNodeItemFrom from);
}