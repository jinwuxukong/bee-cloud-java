package com.hutu.modules.app.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.center.entity.StatisticsPatientVo;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.common.entity.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 医生患者信息表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-10-14
 */
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
    List<StatisticsPatientVo> statisticsPatient(@Param("userId") Integer userId, @Param("dayNum") int dayNum);

    IPage<PatientVo> pagePatient(Page page, Map<String, Object> params);

    PatientVo read(@Param("id") Integer id, @Param("userId") Integer userId);

    List<Patient> getList(@Param("date")Patient date);
}