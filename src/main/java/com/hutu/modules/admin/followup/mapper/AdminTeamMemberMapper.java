package com.hutu.modules.admin.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.admin.followup.vo.AdminTeamVo;
import com.hutu.modules.app.team.entity.TeamMemberVo;
import com.hutu.modules.common.entity.Team;
import com.hutu.modules.common.entity.TeamMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 医生团队成员表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-12-26
 */
@Mapper
public interface AdminTeamMemberMapper extends BaseMapper<TeamMember> {

    List<TeamMember> getList(@Param("date") TeamMember date);

    IPage<TeamMemberVo> getPage(Page objectPage, Map<String, Object> params);

    TeamMemberVo getOne(@Param("date") TeamMember date);

    IPage<AdminTeamVo> DoctorTeamPage(Page objectPage, Map<String, Object> params);
}