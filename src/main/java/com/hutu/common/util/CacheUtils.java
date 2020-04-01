package com.hutu.common.util;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.embedded.LinkedHashMapCacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
public class CacheUtils {

    final static Cache<String, String> defaultCache = LinkedHashMapCacheBuilder.createLinkedHashMapCacheBuilder()
            .limit(100)
            .expireAfterWrite(300, TimeUnit.SECONDS)
            .buildCache();

    public static String get(String key) {
        return defaultCache.get(key);
    }

    public static void put(String key, String value) {
        defaultCache.put(key, value);
    }
    public final static Cache<String, String> userCache = LinkedHashMapCacheBuilder.createLinkedHashMapCacheBuilder()
            .limit(100)
            .buildCache();
}
