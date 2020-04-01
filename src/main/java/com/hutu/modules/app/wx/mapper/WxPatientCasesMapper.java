package com.hutu.modules.app.wx.mapper;

import com.hutu.modules.common.entity.PatientCases;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 患者病历信息 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-12-27
 */
@Mapper
public interface WxPatientCasesMapper extends BaseMapper<PatientCases> {

}