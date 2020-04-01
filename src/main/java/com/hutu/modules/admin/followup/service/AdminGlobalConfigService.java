package com.hutu.modules.admin.followup.service;

import com.hutu.modules.common.entity.GlobalConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 系统配置表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */
public interface AdminGlobalConfigService extends IService<GlobalConfig> {

    Map<String, Map<String, String>> getValuesByKeys(String keys);

    Map<String, Map<String, String>> getValueByKey(String key);
}
