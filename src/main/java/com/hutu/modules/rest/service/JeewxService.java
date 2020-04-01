package com.hutu.modules.rest.service;

import com.hutu.modules.common.entity.PlanNodeItem;

/**
 * 调用捷微接口，或者被捷微系统调用的接口服务类
 */
public interface JeewxService {

    /**
     * 发送微信模板消息
     * @param planNodeItem
     * @return
     */
    Boolean sendFormTemplateMessage(PlanNodeItem planNodeItem);

    /**
     * 若取消订阅，就更新openId和临时表doctorId的绑定关系 删除置为1
     *
     * @param openId 取消关注的openId
     * @return
     */
    boolean delBindUnSubscribe(String openId) throws Exception;

    /**
     * 处理关注公众号事件
     * @param openId 微信openId
     * @param parameter 入参集合
     * @return 是否处理成功
     */
    String dealSubEvent(String openId, String parameter);
}
