package com.hutu.modules.app.wx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hutu.modules.app.wx.vo.WxDoctorInfoVo;
import com.hutu.modules.common.entity.Doctor;
import com.hutu.modules.common.entity.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 医生患者信息表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-12-27
 */
@Mapper
public interface WxDoctorMapper extends BaseMapper<Doctor> {

    WxDoctorInfoVo getDoctorInfoByUserId(@Param("id") Integer id);
}