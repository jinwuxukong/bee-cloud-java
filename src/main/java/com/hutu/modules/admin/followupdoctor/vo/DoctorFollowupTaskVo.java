package com.hutu.modules.admin.followupdoctor.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DoctorFollowupTaskVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 表单id
     */
    public Integer id;
    /**
     * 表单名字
     */
    public String name;
    /**
     * 计划id
     */
    private Integer planId;
    /**
     * 节点id
     */
    private Integer nodeId;
    /**
     * 病人的id
     */
    private Integer patientId;

    /**
     * 发送的内容
     */
    private String content;

    /**
     * 表单类型
     */
    public Integer type;
    /**
     * 节点名字
     */
    public String nodeName;
    /**
     * 计划名字
     */
    public String planName;

    /**
     * 患者名字
     */
    public String patientName;
}
