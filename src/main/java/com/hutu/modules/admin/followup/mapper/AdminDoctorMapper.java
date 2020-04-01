package com.hutu.modules.admin.followup.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.admin.followup.vo.DoctorVo;
import com.hutu.modules.app.team.entity.TeamVo;
import com.hutu.modules.common.entity.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface AdminDoctorMapper extends BaseMapper<Doctor> {

    IPage<DoctorVo> getPage(Page objectPage, Map<String, Object> params);
}
