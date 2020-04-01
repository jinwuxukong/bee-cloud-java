package com.hutu.modules.admin.upms.service;

import com.hutu.modules.admin.upms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-08-16
 */
public interface UserService extends IService<User> {
    boolean saveUser(User user);

    boolean updateUser(User user);


}
