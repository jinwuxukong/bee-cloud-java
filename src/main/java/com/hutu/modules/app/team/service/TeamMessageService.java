package com.hutu.modules.app.team.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.message.entity.MessageFrom;
import com.hutu.modules.common.entity.Message;
import com.hutu.modules.common.entity.TeamMessage;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 团队消息表 服务类
 * </p>
 *
 * @author generator
 * @since 2020-02-17
 */
public interface TeamMessageService extends IService<TeamMessage> {

    boolean create(TeamMessage data);

    boolean updateByid(TeamMessage data);

    boolean createMessage(MessageFrom data);

    void utilMessage(Integer num, List<MessageFrom> data);

    Integer isMessage(Map<String, Object> params);
}
