package com.hutu.modules.admin.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.common.annotation.OperationLog;
import com.hutu.common.enums.OperateEnum;
import com.hutu.modules.admin.upms.entity.User;
import com.hutu.modules.admin.upms.entity.UserRole;
import com.hutu.modules.admin.upms.mapper.UserMapper;
import com.hutu.modules.admin.upms.service.UserRoleService;
import com.hutu.modules.admin.upms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-08-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserRoleService userRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(value = "新增用户", type = OperateEnum.ADD, module = "com.hutu.modules.upms")
    public boolean saveUser(User user) {
        save(user);
        Integer[] roles = user.getRoles();
        if (roles != null && roles.length > 0) {
            ArrayList<UserRole> userRoles = new ArrayList<>();
            getUserRoleList(user, roles, userRoles);
            userRoleService.saveBatch(userRoles);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(value = "更新用户", type = OperateEnum.UPDATE, module = "com.hutu.modules.upms")
    public boolean updateUser(User user) {
        updateById(user);
        Integer[] roles = user.getRoles();
        if (roles != null) {
            userRoleService.remove(new QueryWrapper<UserRole>().eq("userId", user.getId()));
            ArrayList<UserRole> userRoles = new ArrayList<>();
            getUserRoleList(user, roles, userRoles);
            userRoleService.saveBatch(userRoles);
        }
        return true;
    }


    private void getUserRoleList(User user, Integer[] roles, ArrayList<UserRole> userRoles) {
        for (Integer role : roles) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(role);
            userRole.setUserId(user.getId());
            userRoles.add(userRole);
        }
    }
}
