package com.hutu.modules.app.team.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.app.team.mapper.TeamMessageItemMapper;
import com.hutu.modules.app.team.service.TeamMessageItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.team.service.TeamMessageService;
import com.hutu.modules.common.entity.TeamMessage;
import com.hutu.modules.common.entity.TeamMessageItem;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 团队消息详情表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2020-02-17
 */
@Service
public class TeamMessageItemServiceImpl extends ServiceImpl<TeamMessageItemMapper, TeamMessageItem> implements TeamMessageItemService {

    @Autowired
    private TeamMessageService messageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(TeamMessageItem data) {
        TeamMessage message = new TeamMessage();
        message.setId(data.getMessageId());
        messageService.updateByid(message);
        return this.save(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean readUpdateByid(String id) {
        this.update(new UpdateWrapper<TeamMessageItem>().eq("id",id).set("isRead",1));
        TeamMessageItem messageItem = this.getById(id);
        TeamMessage message = messageService.getById(messageItem.getMessageId());
        if(message.getStatusNum()!=0){
            message.setStatusNum(message.getStatusNum()-1<0?0:message.getStatusNum()-1);
        }
        return messageService.update(message,new UpdateWrapper<TeamMessage>().eq("id",message.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean readUpdateByIds(Integer parameterType,Integer teamId) {
        List<TeamMessageItem> list = new ArrayList<>();
        if(teamId!=null){
            TeamMessage message = messageService.getOne(new QueryWrapper<TeamMessage>().eq("parameterType", parameterType).eq("teamId", teamId));
            if(message==null){
                return false;
            }
            list = this.list(new QueryWrapper<TeamMessageItem>().eq("messageId",message.getId()).eq("parameterType", parameterType));
        }else{
            list = this.list(new QueryWrapper<TeamMessageItem>().eq("parameterType", parameterType).eq("toUserId", JwtUtils.getUserId()));
        }
        if(list.size()<1){
            return false;
        }
        int count = 0 ;
        for (TeamMessageItem messageItem : list) {
            if(messageItem.getIsRead()!=0){
                continue;
            }
            this.update(new UpdateWrapper<TeamMessageItem>().eq("id",messageItem.getId()).set("isRead",1));
            count++;
        }
        TeamMessage message = messageService.getById(list.get(0).getMessageId());
        if(message.getStatusNum()!=0){
            messageService.update(new UpdateWrapper<TeamMessage>().set("statusNum",message.getStatusNum()-count<0?0:message.getStatusNum()-count).eq("id",message.getId()));
            return true;
        }else{
            return false;
        }

    }
}
