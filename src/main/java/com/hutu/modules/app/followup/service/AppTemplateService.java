package com.hutu.modules.app.followup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.common.entity.Template;

import java.util.List;

/**
 * <p>
 * 表单模板表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */
public interface AppTemplateService extends IService<Template> {

    IPage getPage(Page page, String keyWord, String type, String formType, Integer userId);

    List<Template> getList(Template date);
}
