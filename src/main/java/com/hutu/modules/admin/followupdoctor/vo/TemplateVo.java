package com.hutu.modules.admin.followupdoctor.vo;

import com.hutu.modules.common.entity.Template;
import lombok.Data;

@Data
public class TemplateVo extends Template {
    /**
     * 平台方案是否已经添加在我的方案中
     */
    private int hadAdd;
}
