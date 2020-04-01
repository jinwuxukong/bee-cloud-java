package com.hutu.modules.admin.upms.service;

import com.hutu.modules.admin.upms.entity.Organization;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 组织 服务类
 * </p>
 *
 * @author generator
 * @since 2019-08-16
 */
public interface OrganizationService extends IService<Organization> {
    List getOrganizationTree();
}
