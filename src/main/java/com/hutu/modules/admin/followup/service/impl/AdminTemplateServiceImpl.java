package com.hutu.modules.admin.followup.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.common.entity.Template;
import com.hutu.modules.admin.followup.mapper.AdminTemplateMapper;
import com.hutu.modules.admin.followup.service.AdminTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 表单模板表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */
@Service
public class AdminTemplateServiceImpl extends ServiceImpl<AdminTemplateMapper, Template> implements AdminTemplateService {

    @Autowired
    private AdminTemplateMapper mapper;

    @Override
    public List<Template> getList(Template data) {
        return mapper.getList(data);
    }

    @Override
    public IPage<Template> getPage(Page objectPage, Map<String, Object> params) {
        return mapper.getPage(objectPage,params);
    }

    @Override
    public List<Template> getVersionList(Integer id,String templateNumber) {
        return mapper.getVersionList(id,templateNumber);
    }

    @Override
    public Float getVersion(String templateNumber) {
        return mapper.getVersion(templateNumber);
    }
}
