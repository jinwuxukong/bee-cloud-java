package com.hutu.modules.admin.dictionaries.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.admin.dictionaries.entity.AdminHospitalDepartmentVo;
import com.hutu.modules.common.entity.HospitalDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
  * 医院科室表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-12-05
 */
@Mapper
public interface AdminHospitalDepartmentMapper extends BaseMapper<HospitalDepartment> {

    IPage<AdminHospitalDepartmentVo> pageVo(Page page, @Param("params") Map<String, Object> params);
}