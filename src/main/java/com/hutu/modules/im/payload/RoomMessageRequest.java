package com.hutu.modules.im.payload;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RoomMessageRequest {
    /**
     * 消息发送方用户id
     */
    private String fromUid;

    /**
     * 消息接收方聊天室id
     */
    private String toRoomId;

    /**
     * 消息内容
     */
    private String message;

}
