package com.hutu.modules.app.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.team.entity.TeamPatientVo;
import com.hutu.modules.common.entity.TeamPatient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 医生团队患者关系中间表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-12-25
 */
@Mapper
public interface TeamPatientMapper extends BaseMapper<TeamPatient> {

    List<TeamPatient> getTeamPatient(@Param("teamPatient") TeamPatient teamPatient);

    IPage<TeamPatientVo> getPage(Page objectPage, Map<String, Object> params);

}