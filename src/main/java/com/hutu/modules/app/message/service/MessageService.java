package com.hutu.modules.app.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.message.entity.MessageFrom;
import com.hutu.modules.common.entity.Message;

import java.util.List;

/**
 * <p>
 * 系统消息表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-21
 */
public interface MessageService extends IService<Message> {

    boolean create(Message data);

    boolean updateByid(Message data);

    boolean createMessage(MessageFrom data);

    void utilMessage(Integer num, List<MessageFrom> data);
}
