package com.hutu.modules.im.handler;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.hutu.modules.im.config.Event;
import com.hutu.modules.im.entity.ImUserLogin;
import com.hutu.modules.im.entity.TalkRecord;
import com.hutu.modules.im.entity.TalkRoom;
import com.hutu.modules.im.payload.RoomMessageRequest;
import com.hutu.modules.im.payload.SingleMessageRequest;
import com.hutu.modules.im.service.ImUserLoginService;
import com.hutu.modules.im.service.TalkRecordService;
import com.hutu.modules.im.service.TalkRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 发送消息处理类
 * @author hutu
 * @date 2019-11-28 15:08
 */
@Component
@Slf4j
public class MessageEventHandler {
    @Autowired
    private SocketIOServer server;

    @Autowired
    private TalkRecordService talkRecordService;

    @Autowired
    private TalkRoomService talkRoomService;

    @Autowired
    private ImUserLoginService imUserLoginService;

    /**
     * 添加connect事件，当客户端发起连接时调用
     *
     * @param client 客户端对象
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        if (client != null) {
            // 模拟用户id 和token一致
            String userId = client.getHandshakeData().getSingleUrlParam("token");
            String roomId = client.getHandshakeData().getSingleUrlParam("roomId");
            UUID sessionId = client.getSessionId();
            if (StrUtil.isEmpty(userId)){
                log.error("用户id为空");
                return;
            }

            ImUserLogin user = imUserLoginService.getById(userId);
            if (user == null) {
                imUserLoginService.save(new ImUserLogin().setUserId(Integer.valueOf(userId)).setSessionId(sessionId.toString()).setStatus(1));
            }else {
                imUserLoginService.updateById(new ImUserLogin().setUserId(Integer.valueOf(userId)).setSessionId(sessionId.toString()).setStatus(1));
            }
            if (StrUtil.isEmpty(roomId)){
                log.info("房间id为空");
                return;
            }
            // 发送所有未发送成功的消息
            List<TalkRecord> list = talkRecordService.list(new QueryWrapper<TalkRecord>().eq("status", 0).eq("toUserId", userId).eq("roomId",roomId));
            if (list != null && list.size() > 0) {
                list.forEach(obj -> {
                    sendToSingle(sessionId, new SingleMessageRequest(obj.getFromUserId().toString(), obj.getToUserId().toString(), obj.getContent()));
                    obj.setStatus(1);
                });
                // 更新所有消息发送状态
                talkRecordService.updateBatchById(list);
            }
            log.info("连接成功,【user】= {},【sessionId】= {}", user, sessionId);
        } else {
            log.error("客户端为空");
        }
    }

    /**
     * 添加disconnect事件，客户端断开连接时调用，刷新客户端信息
     *
     * @param client 客户端对象
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        if (client != null) {
            String userId = client.getHandshakeData().getSingleUrlParam("token");
            UUID sessionId = client.getSessionId();

            imUserLoginService.updateById(new ImUserLogin().setUserId(Integer.valueOf(userId)).setStatus(0));
            log.info("客户端断开连接,【user】= {},【sessionId】= {}", userId, sessionId);
            client.disconnect();
        } else {
            log.error("客户端为空");
        }
    }



    /**
     * 发起聊天室聊天
     * @param client
     * @param request
     * @param data
     */
    @OnEvent(value = Event.CHAT)
    public void onChatEvent(SocketIOClient client, AckRequest request, RoomMessageRequest data) {

        TalkRoom talkRoom = talkRoomService.getById(data.getToRoomId());
        if (talkRoom == null) {
            log.error("查无此房间，roomId: {}",data.getToRoomId());
            return;
        }
        String groupUserIds = talkRoom.getGroupUserIds();
        String[] ids = groupUserIds.split(",");
        ArrayList<TalkRecord> talkRecords = new ArrayList<>();

        for (String id : ids) {
            TalkRecord temp = new TalkRecord().setCreateTime(LocalDateTime.now()).setContent(data.getMessage())
                    .setFromUserId(Integer.valueOf(data.getFromUid()))
                    .setToUserId(Integer.valueOf(id))
                    .setRoomId(talkRoom.getId())
                    .setStatus(0);
            if (id.equals(data.getFromUid())){
                temp.setStatus(1);
            }
            talkRecords.add(temp);
        }
        talkRecordService.saveBatch(talkRecords);

        List<ImUserLogin> userList = imUserLoginService.list(new QueryWrapper<ImUserLogin>().eq("status", 1).in("userId", ids));
        if (userList == null || userList.size() == 0) {
            log.info("房间内无人在线");
            return;
        }
        sendRoomMessage(data, talkRecords, userList);

        request.sendAckData(Dict.create()
                .set("flag", true)
                .set("message", "发送成功"));
    }

    /**
     * 异步发送消息给群里的其他人
     * @param data
     * @param talkRecords
     * @param userList
     */
    @Async
    public void sendRoomMessage(RoomMessageRequest data, ArrayList<TalkRecord> talkRecords, List<ImUserLogin> userList) {
        userList.forEach(obj -> {
            sendToSingle(UUID.fromString(obj.getSessionId()), new SingleMessageRequest(data.getFromUid(), obj.getUserId().toString(), data.getMessage()));
            for (TalkRecord temp : talkRecords) {
                if (temp.getToUserId().equals(obj.getUserId()) && temp.getStatus().equals(0)) {
                    temp.setStatus(1);
                    talkRecordService.updateById(temp);
                }
            }
        });
    }

    /**
     * 给一个客户端发消息
     */
    public void sendToSingle(UUID sessionId, SingleMessageRequest message) {
        server.getClient(sessionId).sendEvent(Event.CHAT, message);
    }
}
