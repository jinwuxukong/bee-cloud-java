package com.hutu.modules.im.config;

import com.hutu.modules.im.entity.ImUserLogin;
import com.hutu.modules.im.service.ImUserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 模拟数据库
 * </p>
 *
 * @package: com.xkcoding.websocket.socketio.config
 * @description: 模拟数据库
 * @author: yangkai.shen
 * @date: Created in 2018-12-18 19:12
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Slf4j
@Component
public class DbTemplate {
    @Autowired
    private ImUserLoginService imUserLoginService;

    /**
     * 获取所有SessionId
     *
     * @return SessionId列表
     */
    public List<UUID> findAll() {
        List<ImUserLogin> list = imUserLoginService.list();
        ArrayList<UUID> uuids = new ArrayList<>();
        list.forEach(obj->uuids.add(UUID.fromString(obj.getSessionId())));
        return uuids;
    }

    /**
     * 根据UserId查询SessionId
     *
     * @param userId 用户id
     * @return SessionId
     */
    public String findByUserId(String userId) {

        ImUserLogin user = imUserLoginService.getById(userId);
        return user!=null?user.getSessionId():null;
    }

    /**
     * 保存/更新 user_id <-> session_id 的关系
     *
     * @param userId    用户id
     * @param sessionId SessionId
     */
    public void login(String userId, UUID sessionId) {
        ImUserLogin user = imUserLoginService.getById(userId);
        if (user == null) {
            imUserLoginService.save(new ImUserLogin().setUserId(Integer.valueOf(userId)).setSessionId(sessionId.toString()).setStatus(1));
        }else {
            imUserLoginService.updateById(new ImUserLogin().setUserId(Integer.valueOf(userId)).setSessionId(sessionId.toString()).setStatus(1));
        }
    }
    /**
     * 删除 user_id <-> session_id 的关系
     *
     * @param userId 用户id
     */
    public void outLogin(String userId) {
        imUserLoginService.updateById(new ImUserLogin().setUserId(Integer.valueOf(userId)).setStatus(0));
    }

}
