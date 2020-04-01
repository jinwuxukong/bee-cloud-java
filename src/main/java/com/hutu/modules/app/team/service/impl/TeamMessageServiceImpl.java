package com.hutu.modules.app.team.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.admin.upms.service.UserService;
import com.hutu.modules.app.message.entity.MessageFrom;
import com.hutu.modules.app.team.mapper.TeamMessageMapper;
import com.hutu.modules.app.team.service.TeamMessageItemService;
import com.hutu.modules.app.team.service.TeamMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.common.entity.Message;
import com.hutu.modules.common.entity.TeamMessage;
import com.hutu.modules.common.entity.TeamMessageItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 团队消息表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2020-02-17
 */
@Service
@Slf4j
public class TeamMessageServiceImpl extends ServiceImpl<TeamMessageMapper, TeamMessage> implements TeamMessageService {

    @Autowired
    private TeamMessageMapper mapper;
    @Autowired
    private TeamMessageItemService teamMessageItemService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(TeamMessage data) {
        int isRead = teamMessageItemService.count(new QueryWrapper<TeamMessageItem>().eq("parameterType",data.getParameterType()).eq("toUserId", JwtUtils.getUserId()).eq("isRead", 0));
        data.setStatusNum(isRead);
        return this.save(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByid(TeamMessage data) {
        TeamMessage message = this.getById(data.getId());
        int isRead = teamMessageItemService.count(new QueryWrapper<TeamMessageItem>().eq("isRead", 0));
        message.setStatusNum(isRead);
        return this.update(message,new UpdateWrapper<TeamMessage>().eq("id",message.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createMessage(MessageFrom data) {
        Integer userId = data.getToUserId();
        TeamMessage message = new TeamMessage();
        TeamMessage message1 = this.getOne(new QueryWrapper<TeamMessage>().eq(data.getTeamId()!=null,"teamId",data.getTeamId()).eq("parameterType",data.getParameterType()));
        if(message1!=null){
            this.update(new UpdateWrapper<TeamMessage>()
                    .set("statusNum",message1.getStatusNum()!=0?message1.getStatusNum()+1:1)
                    .set("content",data.getTitle()+" "+data.getContent())
                    .set("subtitle",data.getSubtitle())
                    .eq("id",message1.getId()));
            BeanUtils.copyProperties(message1,message);
        }else{
            TeamMessage Message = new TeamMessage();
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
        TeamMessageItem messageItem = new TeamMessageItem();
        messageItem.setMessageId(message.getId());
        messageItem.setItemType(data.getItemType());
        messageItem.setParameterType(data.getParameterType());
        messageItem.setParameter(data.getParameter());
        messageItem.setIsRead(0);
        messageItem.setTitle(data.getTitle());
        messageItem.setContent(data.getContent());
        messageItem.setToUserId(data.getToUserId());
        return teamMessageItemService.save(messageItem);
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

    @Override
    public Integer isMessage(Map<String, Object> params) {
        return mapper.isMessage(params);
    }
}
