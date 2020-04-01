package com.hutu.modules.app.wx.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Accessors(chain = true)
public class WxPatientFollowupRecordVo implements Serializable {
    /**
     * 随访记录主键id
     */
    String id;
    /**
     * 表单名称
     */
    String name;
    /**
     * 完成状态（0 待完成，1 已完成，2，超时，3已终止）
     */
    String status;
    /**
     * 表单类型（1问题表单，2宣教内容）
     */
    Integer type;
    /**
     * 表单类型
     */
    String typeShow;
    /**
     * 表单类型(按科室分类)
     */
    Integer formType;
    /**
     * 表单类型
     */
    String formTypeShow;
    /**
     * 填表时间
     */
    Date updateTime;

}
