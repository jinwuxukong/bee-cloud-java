package com.hutu.modules.app.followup.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.followup.mapper.AppTemplateMapper;
import com.hutu.modules.app.followup.service.AppTemplateService;
import com.hutu.modules.common.entity.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 表单模板表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */
@Service
public class AppTemplateServiceImpl extends ServiceImpl<AppTemplateMapper, Template> implements AppTemplateService {

    @Autowired
    private AppTemplateMapper appTemplateMapper;
    @Autowired
    private AppTemplateMapper mapper;

    @Override
    public IPage<Template> getPage(Page page, String keyWord, String type, String formType, Integer userId) {
        return appTemplateMapper.getPage(page,keyWord,type,formType,userId);
    }



    @Override
    public List<Template> getList(Template data) {
        return mapper.getList(data);
    }
}
