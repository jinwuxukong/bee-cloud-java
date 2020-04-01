package com.hutu.modules.admin.followup.service.impl;

import com.hutu.modules.common.entity.LoginLog;
import com.hutu.modules.admin.followup.mapper.AdminLoginLogMapper;
import com.hutu.modules.admin.followup.service.AdminLoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author generator
 * @since 2020-03-17
 */
@Service
public class AdminLoginLogServiceImpl extends ServiceImpl<AdminLoginLogMapper, LoginLog> implements AdminLoginLogService {

    @Autowired
    private AdminLoginLogMapper mapper;

    @Override
    public List<LoginLog> getList(LoginLog data) {
        return mapper.getList(data);
    }
}
