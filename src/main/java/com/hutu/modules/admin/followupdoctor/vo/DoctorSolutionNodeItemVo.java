package com.hutu.modules.admin.followupdoctor.vo;

import com.hutu.modules.common.entity.SolutionNodeItem;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DoctorSolutionNodeItemVo extends SolutionNodeItem {
    private static final long serialVersionUID = 1L;
    private Integer id;
    /**
     * item名字
     */
    private String name;

    private String typeShow;
    /**
     * 模板类型 1，量表，2 宣教
     */
    private Integer templateType;
}
