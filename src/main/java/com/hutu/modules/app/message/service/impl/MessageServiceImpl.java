package com.hutu.modules.app.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.admin.upms.service.UserService;
import com.hutu.modules.app.message.entity.MessageFrom;
import com.hutu.modules.app.message.mapper.MessageMapper;
import com.hutu.modules.app.message.service.MessageItemService;
import com.hutu.modules.app.message.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.common.entity.Message;
import com.hutu.modules.common.entity.MessageItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 系统消息表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-21
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageItemService messageItemService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(Message data) {
        int isRead = messageItemService.count(new QueryWrapper<MessageItem>().eq("type", data.getType()).eq("parameterType",data.getParameterType()).eq("toUserId",JwtUtils.getUserId()).eq("isRead", 0));
        data.setStatusNum(isRead);
        return this.save(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByid(Message data) {
        Message message = this.getById(data.getId());
        int isRead = messageItemService.count(new QueryWrapper<MessageItem>().eq("isRead", 0));
        message.setStatusNum(isRead);
        return this.update(message,new UpdateWrapper<Message>().eq("id",message.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createMessage(MessageFrom data) {
        Integer userId = data.getToUserId();
        Message message = new Message();
        Message message1 = this.getOne(new QueryWrapper<Message>().eq(data.getTeamId()!=null,"teamId",data.getTeamId()).eq("toUserId", userId).eq("type", data.getType()).eq("parameterType",data.getParameterType()));
        if(message1!=null){
            this.update(new UpdateWrapper<Message>()
                    .set("statusNum",message1.getStatusNum()!=0?message1.getStatusNum()+1:1)
                    .set("content",data.getTitle()+" "+data.getContent())
                    .set("subtitle",data.getSubtitle())
                    .eq("id",message1.getId()));
            BeanUtils.copyProperties(message1,message);
        }else{
            Message Message = new Message();
            Message.setType(data.getType());
            Message.setParameterType(data.getParameterType());
            Message.setTitle(data.getFirstTitle());
            Message.setContent(data.getTitle()+" "+data.getContent());
            Message.setSubtitle(data.getSubtitle());
            Message.setStatusNum(1);
            Message.setTeamId(data.getTeamId());
            Message.setToUserId(data.getToUserId());
            this.save(Message);
            BeanUtils.copyProperties(Message,message);
        }
        MessageItem messageItem = new MessageItem();
        messageItem.setMessageId(message.getId());
        messageItem.setType(data.getType());
        messageItem.setItemType(data.getItemType());
        messageItem.setParameterType(data.getParameterType());
        messageItem.setParameter(data.getParameter());
        messageItem.setIsRead(0);
        messageItem.setTitle(data.getTitle());
        messageItem.setContent(data.getContent());
        messageItem.setToUserId(data.getToUserId());
        return messageItemService.save(messageItem);
    }

    public void utilMessage(Integer num, List<MessageFrom> data) {
        ExecutorService executorService = Executors.newFixedThreadPool(num);
        for (MessageFrom datum : data) {
            CompletableFuture<String> messageCreate = CompletableFuture.supplyAsync(() -> {
                try {
                    createMessage(datum);
                    return "===================推送消息成功===================";
                } catch (Exception e) {
                    System.err.println("推送消息遭遇了不测");
                    System.err.println(e.getMessage());
                    return "===================推送消息失败===================";
                }
            }, executorService);
            messageCreate.thenAccept((e) -> {
                log.info(e);
            });
        }
    }
}
