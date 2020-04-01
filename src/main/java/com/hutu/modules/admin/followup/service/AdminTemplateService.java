package com.hutu.modules.admin.followup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.common.entity.Template;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 表单模板表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */
public interface AdminTemplateService extends IService<Template> {

    List<Template> getList(Template date);

    IPage<Template> getPage(Page objectPage, Map<String, Object> params);

    List<Template> getVersionList(Integer id,String templateNumber);

    Float getVersion(String templateNumber);
}
