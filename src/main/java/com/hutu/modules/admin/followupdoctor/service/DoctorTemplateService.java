package com.hutu.modules.admin.followupdoctor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.common.entity.Template;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 表单模板表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */
public interface DoctorTemplateService extends IService<Template> {

    IPage<Template> getMyPage(Page<Template> page, String keyWord, String type, String formTypes);

    IPage getPlateFormPage(Page page, String keyWord, String type, String formTypes, Integer scope);

    R addToMyTemplate(int id);
}
