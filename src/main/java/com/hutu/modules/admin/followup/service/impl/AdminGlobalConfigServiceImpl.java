package com.hutu.modules.admin.followup.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hutu.modules.common.entity.GlobalConfig;
import com.hutu.modules.admin.followup.mapper.AdminGlobalConfigMapper;
import com.hutu.modules.admin.followup.service.AdminGlobalConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 系统配置表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */
@Service
public class AdminGlobalConfigServiceImpl extends ServiceImpl<AdminGlobalConfigMapper, GlobalConfig> implements AdminGlobalConfigService {

    /**
     * 关于我们
     *
     * @param keys
     * @return
     */
    @Override
    public Map<String, Map<String, String>> getValuesByKeys(String keys) {
        if (StrUtil.isNotBlank(keys) && StrUtil.isNotBlank(keys)) {
            Map<String, Map<String, String>> map = new HashMap<>();
            String[] keyArray = keys.split(",");
            for (int i = 0; i < keyArray.length; i++) {
                GlobalConfig globalConfig = getOne(new QueryWrapper<GlobalConfig>().eq("code", keyArray[i]));
                Map<String, String> tmpMap = new HashMap<>();
                tmpMap.put("name", globalConfig.getName());
                tmpMap.put("value", globalConfig.getValue());
                map.put(keyArray[i], tmpMap);
            }
            return map;
        }
        return new HashMap<>();
    }

    /**
     * 通过key获取value
     *
     * @param key
     * @return
     */
    @Override
    public Map<String, Map<String, String>> getValueByKey(String key) {
        return getValuesByKeys(key);
    }
}
