package com.hutu.common.constant;

/**
 * 系统常量
 *
 * @author hutu
 * @date 2019/4/12
 */
public class Constant {
    /**
     * 请求头中token名称
     */
    public final static String TOKEN = "token";
    /**
     * 直营店租户id及组织id
     */
    public final static Integer BOSS_TENANT_ID = 1;

    public final static Integer EXPERT_ROLE_ID = 4;
    public final static Integer MEMBER_ROLE_ID = 3;

    public final static Integer EXPERT_TYPE = 1;
    public final static Integer MEMBER_TYPE = 2;

    public final static Integer YIMI_DOCTOR_TYPE = 3;

    /**
     * 通知第一类型
     */
    //系统消息
    public final static Integer MESSAGE_SYSTEM = 0;
    //随访消息
    public final static Integer MESSAGE_VISIT = 1;
    //团队消息
    public final static Integer MESSAGE_GROUP = 2;
    /**
     * 消息操作类型
     * 0通知类：无操作无交互 1跳转类：可跳转查看详情 2交互类：目前只有同意与拒绝
     */
    //0通知类
    public final static Integer MESSAGE_ITEM_TYPE_NOTICE = 0;
    //1跳转类
    public final static Integer MESSAGE_ITEM_TYPE_JUMP = 1;
    //2交互类
    public final static Integer MESSAGE_ITEM_TYPE_INTERACTIVE = 2;
    /**
     * 通知第二类型
     */
    //系统升级
    public final static Integer MESSAGE_SYSTEM_UPGRADE = 0;
    //计划随访开始
    public final static Integer MESSAGE_VISIT_START = 1;
    //计划随访结束
    public final static Integer MESSAGE_VISIT_END = 2;
    //3随访申请
    public final static Integer MESSAGE_VISIT_APPLY = 3;
    //4已收到量表
    public final static Integer MESSAGE_RECEIVED_SCALE = 4;
    //5患者取消关注
    public final static Integer MESSAGE_SYSTEM_UNFOLLOW = 5;
    //6团队新成员
    public final static Integer MESSAGE_TEAM_NEWCOMER = 6;
    //7团队成员退出
    public final static Integer MESSAGE_TEAM_MEMBER_EXIT = 7;
    //8团队你有新患者
    public final static Integer MESSAGE_TEAM_FOR_YOUR_NEW_PATIENT = 8;
    //9团队申请加入
    public final static Integer MESSAGE_TEAM_APPLY_JOIN = 9;
    //10团队删除成员
    public final static Integer MESSAGE_TEAM_DELETE_MEMBER = 10;
    //11团队公告
    public final static Integer MESSAGE_TEAM_NOTICE = 11;
    //12团队医生新患者
    public final static Integer MESSAGE_TEAM_DOCTOR_NEW_PATIENT = 12;

    /**
     * 日间手术创新大赛 活动id
     */
    public final static Integer oneActivityId = 1;

    public final static Integer sysType = 0;
    public final static Integer expertType = 1;
    public final static Integer hospitalType = 2;

    public final static String FORM_URL = "http://baidu.com";
}
