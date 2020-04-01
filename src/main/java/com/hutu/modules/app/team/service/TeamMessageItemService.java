package com.hutu.modules.app.team.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.common.entity.TeamMessageItem;

/**
 * <p>
 * 团队消息详情表 服务类
 * </p>
 *
 * @author generator
 * @since 2020-02-17
 */
public interface TeamMessageItemService extends IService<TeamMessageItem> {

    boolean create(TeamMessageItem data);

    boolean readUpdateByid(String id);

    boolean readUpdateByIds(Integer parameterType,Integer Team);
}
