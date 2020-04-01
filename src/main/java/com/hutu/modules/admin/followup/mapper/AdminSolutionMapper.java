package com.hutu.modules.admin.followup.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.common.entity.Solution;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 方案表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Mapper
public interface AdminSolutionMapper extends BaseMapper<Solution> {

    int getVersion(@Param("solutionNumber") String solutionNumber);

    List<Solution> getList(@Param("date")Solution date);

    IPage<Solution> getPage(Page objectPage,@Param("params") Map<String, Object> params);

    List<Solution> getVersionList(@Param("id")Integer id,@Param("solutionNumber") String solutionNumber);
}