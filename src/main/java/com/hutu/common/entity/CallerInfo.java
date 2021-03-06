package com.hutu.common.entity;

import java.io.Serializable;

/**
 * 调用者信息
 * @author hutu
 * @date 2018/8/8 16:49
 */
public class CallerInfo implements Serializable {

    /**
     * 用户ID
     */
    public Integer uid  = null;
    /**
     * 用户名
     */
    public String name = "";
    /**
     * 昵称
     */
    public String nick = "";
    /**
     * 用户类型
     */
    public Integer type = null;
    /**
     * 租户id
     */
    public Integer tenantId = null;

}
