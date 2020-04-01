package com.hutu.modules.admin.followup.service;

import com.hutu.modules.common.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author generator
 * @since 2020-03-17
 */
public interface AdminLoginLogService extends IService<LoginLog> {

    List<LoginLog> getList(LoginLog date);
}
