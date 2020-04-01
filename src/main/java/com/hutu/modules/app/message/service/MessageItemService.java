package com.hutu.modules.app.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.common.entity.MessageItem;

/**
 * <p>
 * 个人消息表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-21
 */
public interface MessageItemService extends IService<MessageItem> {

    boolean create(MessageItem data);

    boolean readUpdateByid(String id);

    boolean readUpdateByIds(Integer type,Integer parameterType,Integer Team);
}
