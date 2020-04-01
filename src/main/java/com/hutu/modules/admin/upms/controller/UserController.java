package com.hutu.modules.admin.upms.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.common.validator.group.UpdateGroup;
import com.hutu.modules.admin.upms.entity.User;
import com.hutu.modules.admin.upms.entity.UserRole;
import com.hutu.modules.admin.upms.service.OrganizationService;
import com.hutu.modules.admin.upms.service.UserRoleService;
import com.hutu.modules.admin.upms.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */

@Api(tags = "系统用户表")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("所在部门") @RequestParam(required = false) String departmentId, @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Page<User> page = new Page<>(current, pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        queryWrapper.eq(StrUtil.isNotEmpty(departmentId), "departmentId", departmentId);
        userService.page(page, queryWrapper);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{id}")
    public R delete(@ApiParam("数据对象id") @PathVariable("id") String id) {
        return R.error("删除功能有待商榷！");
    }

    @ApiOperation("新增")
    @PostMapping("/create")
    public R create(@RequestBody @ApiParam("数据对象") User data) {
        return userService.saveUser(data) ? R.ok() : R.error("新增错误");
    }

    @ApiOperation("更新")
    @PostMapping("/update")
    public R update(@RequestBody @ApiParam("数据对象") @Validated(UpdateGroup.class) User data) {
        return userService.updateUser(data) ? R.ok() : R.error("更新错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") String id) {
        return R.ok().put("info", userService.getById(id));
    }

    @Autowired
    UserRoleService userRoleService;

    @ApiOperation("通过ID获取角色集合")
    @GetMapping("/roles/{id}")
    public R getRolesByUserId(@ApiParam("数据对象id") @PathVariable("id") String id) {
        List<UserRole> list = userRoleService.list(new QueryWrapper<UserRole>().eq("userId", id));
        Object[] roles = null;
        if (list != null && list.size() > 0) {
            roles = list.stream().map(userRole -> userRole.getRoleId()).toArray();
        }
        return R.ok().put("roles", roles);
    }

    @Autowired
    OrganizationService organizationService;

    @ApiOperation("获取组织树")
    @GetMapping("/getOrgTree")
    public R getPermissionTree() {
        List treeData = organizationService.getOrganizationTree();
        return R.ok().put("treeData", treeData);
    }

    @ApiOperation("获取用户手机号")
    @AuthIgnore
    @GetMapping("/getPhone")
    public R getPhone(String id) {
        User user = userService.getById(id);
        return R.ok().put("phone", user.getPhone());
    }

    @ApiOperation("修改密码")
    @GetMapping("resetPassword")
    public R resetPassword(String password) {
        Integer uid = JwtUtils.getCallerInfo().uid;
        User user = new User();
        user.setId(uid);
        user.setPass(password);
        return R.ok().put("list", userService.updateById(user));
    }


    @ApiOperation("保存")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") User data) {
        boolean result;
        if (data.getId()==null){
            result = userService.saveUser(data);
        }else {
            result = userService.updateUser(data);
        }
        return result?R.ok():R.error("保存错误");
    }
}
