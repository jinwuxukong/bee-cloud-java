package com.hutu.modules.admin.followupdoctor.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.admin.followupdoctor.vo.DoctorSolutionVo;
import com.hutu.modules.common.entity.Solution;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 方案表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Mapper
public interface DoctorSolutionMapper extends BaseMapper<Solution> {

}