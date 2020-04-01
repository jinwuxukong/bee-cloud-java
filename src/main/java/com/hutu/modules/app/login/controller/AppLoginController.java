package com.hutu.modules.app.login.controller;

import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.CallerInfo;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.admin.followup.form.DoctorRegisterForm;
import com.hutu.modules.admin.login.dto.UserInfo;
import com.hutu.modules.admin.upms.entity.Organization;
import com.hutu.modules.admin.upms.service.OrganizationService;
import com.hutu.modules.app.login.service.AppLoginService;
import com.hutu.modules.app.login.dto.AppRegisterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 登录逻辑
 *
 * @author hutu
 * @date 2018/8/16 9:33
 */
@Api(tags = "APP登录控制器")
@RestController
public class AppLoginController {

    @Autowired
    AppLoginService appLoginService;
    @Autowired
    OrganizationService organizationService;

    @AuthIgnore
    @ApiOperation("注册")
    @PostMapping("APP/register")
    public R register(@ApiParam("注册对象") @RequestBody AppRegisterVo register) {
        return appLoginService.register(new DoctorRegisterForm().setPhone(register.getPhone()).setPassword(register.getPassword()));
    }

    @ApiOperation("登录接口")
    @AuthIgnore
    @GetMapping("AppLogin")
    public R login(String username, String password) {
        return appLoginService.login(username, password);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("AppGetUserInfo")
    public R getUserInfo() {
        Organization organization = organizationService.getById(JwtUtils.getCallerInfo().tenantId);
        UserInfo userInfo = new UserInfo();
        CallerInfo callerInfo = JwtUtils.getCallerInfo();
        userInfo.tenantId = callerInfo.tenantId;
        userInfo.uid = callerInfo.uid;
        userInfo.name = callerInfo.name;
        userInfo.nick = callerInfo.nick;
        userInfo.type = callerInfo.type;
        userInfo.setOrgName(organization == null ? "unknown" : organization.getName());
        return R.ok().put("user", userInfo);
    }

    @ApiOperation("获取用户权限信息")
    @GetMapping("AppGetPermissionByUserId")
    public R getPermissionByUserId() {
        return R.ok().put("list", appLoginService.getPermissionByUserId(JwtUtils.getCallerInfo().uid));
    }

    @ApiOperation("获取角色权限树")
    @GetMapping("AppGetRolePermissionTree")
    public R getRolePermissionTree(Integer roleId) {
        return R.ok().put("treeData", appLoginService.getPermissionTree()).put("keys", appLoginService.getRolePermissionIds(roleId));
    }

    @ApiOperation("更新角色权限树")
    @PostMapping("AppUpdateRolePermission")
    public R updateRolePermission(@RequestBody Integer[] permissionIds, Integer roleId) {
        return R.ok().put("list", appLoginService.updateRolePermission(permissionIds, roleId));
    }

    @ApiOperation("退出登录")
    @GetMapping("AppLogout")
    public R logout() {
        return R.ok();
    }

}
