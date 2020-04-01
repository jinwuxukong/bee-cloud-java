package com.hutu.modules.app.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.message.mapper.MessageItemMapper;
import com.hutu.modules.app.message.service.MessageItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.message.service.MessageService;
import com.hutu.modules.common.entity.Message;
import com.hutu.modules.common.entity.MessageItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 个人消息表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-21
 */
@Service
public class MessageItemServiceImpl extends ServiceImpl<MessageItemMapper, MessageItem> implements MessageItemService {

    @Autowired
    private MessageService messageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(MessageItem data) {
        Message message = new Message();
        message.setId(data.getMessageId());
        messageService.updateByid(message);
        return this.save(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean readUpdateByid(String id) {
        this.update(new UpdateWrapper<MessageItem>().eq("id",id).set("isRead",1));
        MessageItem messageItem = this.getById(id);
        Message message = messageService.getById(messageItem.getMessageId());
        if(message.getStatusNum()!=0){
            message.setStatusNum(message.getStatusNum()-1<0?0:message.getStatusNum()-1);
        }
        return messageService.update(message,new UpdateWrapper<Message>().eq("id",message.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean readUpdateByIds(Integer type,Integer parameterType,Integer teamId) {
        List<MessageItem> list = new ArrayList<>();
        if(teamId!=null){
            Message message = messageService.getOne(new QueryWrapper<Message>().eq("type", type).eq("parameterType", parameterType).eq("teamId", teamId));
            if(message==null){
                return false;
            }
            list = this.list(new QueryWrapper<MessageItem>().eq("messageId",message.getId()).eq("type", type).eq("parameterType", parameterType));
        }else{
            list = this.list(new QueryWrapper<MessageItem>().eq("type", type).eq("parameterType", parameterType).eq("toUserId", JwtUtils.getUserId()));
        }
        if(list.size()<1){
            return false;
        }
        int count = 0 ;
        for (MessageItem messageItem : list) {
            if(messageItem.getIsRead()!=0){
                continue;
            }
            this.update(new UpdateWrapper<MessageItem>().eq("id",messageItem.getId()).set("isRead",1));
            count++;
        }
        Message message = messageService.getById(list.get(0).getMessageId());
        if(message.getStatusNum()!=0){
            messageService.update(new UpdateWrapper<Message>().set("statusNum",message.getStatusNum()-count<0?0:message.getStatusNum()-count).eq("id",message.getId()));
            return true;
        }else{
            return false;
        }

    }
}
