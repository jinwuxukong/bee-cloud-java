package com.hutu.modules.app.common.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.team.service.TeamMemberService;
import com.hutu.modules.app.team.service.TeamService;
import com.hutu.modules.common.entity.Team;
import com.hutu.modules.common.entity.TeamMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 医生团队表 前端控制器
 *
 * @author generator
 * @since 2019-12-26
 */

@Api(tags = "APP-全局类")
@RestController
@RequestMapping("common")
public class CommonController {
    @Autowired
    private TeamMemberService teamMemberService;

    @ApiOperation("校验团队")
    @PostMapping("/createOrUpdate")
    public R verifyTeam(){
        TeamMember teamMember = teamMemberService.getOne(new QueryWrapper<TeamMember>().eq("userId", JwtUtils.getUserId()));
        if(teamMember!=null){
            return R.ok();
        }else{
            return R.error();
        }
    }
}
