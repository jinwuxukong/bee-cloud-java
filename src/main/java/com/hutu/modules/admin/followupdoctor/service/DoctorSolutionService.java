package com.hutu.modules.admin.followupdoctor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.admin.followupdoctor.vo.DoctorSolutionVo;
import com.hutu.modules.common.entity.Solution;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 方案表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
public interface DoctorSolutionService extends IService<Solution> {


    R createOrUpdate(Solution data);

    IPage<Solution> getMyPage(Page<Solution> page, String keyWord, Integer status, String types);

    IPage getPlateFormPage(Page page, String keyWord, Integer status, String types, Integer scope);

    R addPlateFormSolutionToMe(int solutionId);
}
