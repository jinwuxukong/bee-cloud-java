package com.hutu.modules.app.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.followup.entity.PatientCasesVo;
import com.hutu.modules.common.entity.PatientCases;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 患者病历信息 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2020-01-09
 */
@Mapper
public interface PatientCasesMapper extends BaseMapper<PatientCases> {

    List<PatientCases> getList(@Param("date") PatientCases date);

    IPage<PatientCases> getPage(Page objectPage, Map<String, Object> params);
}