package com.hutu.modules.app.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.modules.common.entity.Solution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 方案表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Mapper
public interface AppSolutionMapper extends BaseMapper<Solution> {

    Integer countStatistics(@Param("userId") Integer userId, @Param("scope") int scope);

    List<Solution> getList(@Param("date")Solution date);
}