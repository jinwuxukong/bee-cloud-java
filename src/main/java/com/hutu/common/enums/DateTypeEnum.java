package com.hutu.common.enums;

/**
 * 时间枚举类型
 * @author hutu
 * @date 2018/9/28
 */
public enum DateTypeEnum {
    /**
     * 天
     */
    DAY(1,"天"),
    WEEK(2,"周"),
    MONTH(3,"月"),
    YEAR(4,"年");
    public int code;
    public String desc;
    DateTypeEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
