package com.hutu.modules.admin.followupdoctor.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.admin.followupdoctor.mapper.DoctorTemplateMapper;
import com.hutu.modules.admin.followupdoctor.service.DoctorTemplateService;
import com.hutu.modules.admin.followupdoctor.vo.TemplateVo;
import com.hutu.modules.common.entity.Template;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
public class DoctorTemplateServiceImpl extends ServiceImpl<DoctorTemplateMapper, Template> implements DoctorTemplateService {
    @Autowired
    private DoctorTemplateMapper templateMapper;

    /**
     * @param page      分页对象
     * @param keyWord   关键词
     * @param type      模板类型 1问题表单，2宣教内容
     * @param formTypes 表单的类型
     * @return
     */
    @Override
    public IPage<Template> getMyPage(Page<Template> page, String keyWord, String type, String formTypes) {
        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("currentOwnerId", JwtUtils.getUserId());
        if (StrUtil.isNotEmpty(keyWord) && StrUtil.isNotBlank(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        if (StrUtil.isNotEmpty(formTypes) && StrUtil.isNotBlank(formTypes)) {
            queryWrapper.in("formType", Arrays.asList(formTypes.split(",")));
        }
        queryWrapper.eq("type", type).orderByDesc("createTime");
        return baseMapper.selectPage(page, queryWrapper);
    }


    /**
     * @param page      分页对象
     * @param keyWord   关键词
     * @param type      模板类型 1问题表单，2宣教内容
     * @param formTypes 表单的类型
     * @param scope     共享范围
     * @return
     */
    @Override
    public IPage getPlateFormPage(Page page, String keyWord, String type, String formTypes, Integer scope) {
        QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
        if (scope != null) {
            queryWrapper.eq("scope", scope);
        }
        if (StrUtil.isNotEmpty(keyWord) && StrUtil.isNotBlank(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        if (StrUtil.isNotEmpty(formTypes) && StrUtil.isNotBlank(formTypes)) {
            queryWrapper.in("formType", Arrays.asList(formTypes.split(",")));
        }
        queryWrapper.eq("type", type).orderByDesc("createTime")
                .select("id","templateNumber", "name", "type", "typeShow", "formType", "formTypeShow", "patientEducationType", "patientEducationTypeShow",
                        "createName", "createId", "createTime", "status", "currentOwnerId", "currentOwnerName", "currentOwnerTime", "verifyId",
                        "verifyName", "verifyTime", "verifyStatus", "scope", "scopeShow");

        IPage iPage = baseMapper.selectPage(page, queryWrapper);

        List<Template> records = iPage.getRecords();

        QueryWrapper<Template> queryWrapper1 = new QueryWrapper<Template>();
        queryWrapper1.eq("currentOwnerId", JwtUtils.getUserId()).select("id", "templateNumber", "createId");
        List<Template> ownTemplates = list(queryWrapper1);

        List<TemplateVo> templateVos = new ArrayList<>();
        //这里做一个比较，拿到当前分页的10条数据，去遍历医生的所有方案
        for (Template record : records) {
            TemplateVo templateVo = new TemplateVo();
            BeanUtils.copyProperties(record, templateVo);
            ownTemplates.stream().forEach(own -> {
                if (own.getTemplateNumber() != null) {
                    if (own.getTemplateNumber().equals(templateVo.getTemplateNumber()) && own.getCreateId().equals(templateVo.getCreateId())) {
                        templateVo.setHadAdd(1);
                    }
                }
            });
            templateVos.add(templateVo);
        }
        long end = System.currentTimeMillis();

        return page.setRecords(templateVos);
    }

    /**
     * 将模板库中的模板添加为我的，重新copy一份出来
     *
     * @param id
     * @return
     */
    @Override
    public R addToMyTemplate(int id) {
        //1，根据id找到对应的模板
        Template oldTemplate = getOne(new QueryWrapper<Template>().eq("id", id));

        //2，id置为null ，保留模板的创建者，将当前拥有者改为他自己，将scope缩小到本人
        Template newTemplate = new Template();
        BeanUtils.copyProperties(oldTemplate, newTemplate);
        newTemplate.setId(null)
                .setCurrentOwnerId(JwtUtils.getUserId())
                .setCurrentOwnerName(JwtUtils.getUserName())
                .setScope(0);

        //3,将做过修改的模板，重新插入到表中
        boolean flag = save(newTemplate);
        return flag ? R.ok() : R.error("模板添加失败");
    }
}
