package com.hutu.modules.admin.followupdoctor.vo;

import com.hutu.modules.common.entity.Solution;
import lombok.Data;

/**
 * 医生端查看平台返回的vo
 */
@Data
public class DoctorSolutionVo extends Solution {

    /**
     * 平台方案是否已经添加在我的方案中
     */
    private int hadAdd;

}
