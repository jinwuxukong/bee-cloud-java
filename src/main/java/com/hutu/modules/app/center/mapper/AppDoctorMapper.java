package com.hutu.modules.app.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.app.followup.entity.PatientVo;
import com.hutu.modules.common.entity.Doctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
  * 医生信息表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */
@Mapper
public interface AppDoctorMapper extends BaseMapper<Doctor> {

    PatientVo WxRead(@Param("openId") String openId);

    IPage<TeamMemberVo> getPage(Page page, Map<String, Object> params);
}
