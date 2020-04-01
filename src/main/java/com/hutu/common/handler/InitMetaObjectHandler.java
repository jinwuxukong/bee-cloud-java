package com.hutu.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hutu.common.util.JwtUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义填充公共 name 字段
 *
 * @author hutu
 * @date 2019/7/11 16:45
 */
@Component
public class InitMetaObjectHandler implements MetaObjectHandler {

    private final static String CREATE_TIME = "createTime";
    private final static String CREATE_ID = "createId";
    private final static String CREATE_NAME = "createName";

    private final static String UPDATE_TIME = "updateTime";
    private final static String UPDATE_ID = "updateId";
    private final static String UPDATE_NAME = "updateName";
    private final static String TENANT_ID = "orgId";

    private final static String CURRENT_OWNER_ID = "currentOwnerId";
    private final static String CURRENT_OWNER_NAME = "currentOwnerName";
    private final static String CURRENT_OWNER_TIME = "currentOwnerTime";

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(CREATE_TIME, metaObject);
        Object createId = getFieldValByName(CREATE_ID, metaObject);
        Object createName = getFieldValByName(CREATE_NAME, metaObject);

        Object updateTime = getFieldValByName(UPDATE_TIME, metaObject);
        Object updateId = getFieldValByName(UPDATE_ID, metaObject);
        Object updateName = getFieldValByName(UPDATE_NAME, metaObject);

        //当前的拥有者信息
        Object currentOwnerId = getFieldValByName(CURRENT_OWNER_ID, metaObject);
        Object currentOwnerName = getFieldValByName(CURRENT_OWNER_NAME, metaObject);
        Object currentOwnerTime = getFieldValByName(CURRENT_OWNER_TIME, metaObject);

        Object tenantId = getFieldValByName(TENANT_ID, metaObject);


        LocalDateTime date = LocalDateTime.now();
        Integer userId = JwtUtils.getUserId();
        String userName = JwtUtils.getUserName();
        Integer userTenantId = JwtUtils.getTenantId();

        if (createTime == null) {
            setFieldValByName(CREATE_TIME, date, metaObject);
        }
        if (createId == null) {
            setFieldValByName(CREATE_ID, userId, metaObject);
        }
        if (createName == null) {
            setFieldValByName(CREATE_NAME, userName, metaObject);
        }

        if (updateTime == null) {
            setFieldValByName(UPDATE_TIME, date, metaObject);
        }
        if (updateId == null) {
            setFieldValByName(UPDATE_ID, userId, metaObject);
        }
        if (updateName == null) {
            setFieldValByName(UPDATE_NAME, userName, metaObject);
        }
        if (tenantId == null) {
            setFieldValByName(TENANT_ID, userTenantId, metaObject);
        }
        //当前的拥有者信息
        if (currentOwnerId == null) {
            setFieldValByName(CURRENT_OWNER_ID, userId, metaObject);
        }
        if (currentOwnerName == null) {
            setFieldValByName(CURRENT_OWNER_NAME, userName, metaObject);
        }
        if (currentOwnerTime == null) {
            setFieldValByName(CURRENT_OWNER_TIME, date, metaObject);
        }
    }

    /**
     * 更新填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        Integer userId = JwtUtils.getUserId();
        String userName = JwtUtils.getUserName();

        setFieldValByName(UPDATE_TIME, LocalDateTime.now(), metaObject);
        setFieldValByName(UPDATE_ID, userId, metaObject);
        setFieldValByName(UPDATE_NAME, userName, metaObject);
    }
}
