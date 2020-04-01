package com.hutu.modules.rest.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 微信模板消息的基本信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class WxTemplateMsg {
    /**
     * 模板id
     */
    private String templateId;

    /**
     * 是接收者openid
     */
    private String toUser;

    /**
     * 模板跳转链接可以拼接参数
     * <p>
     * 我们系统的患教，量表地址拼接患者的id
     * 这个请求是无权限校验的
     */
    private String url;

    /**
     * 传递的数据
     */
    private Map<String,String> data;

}
