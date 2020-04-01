package com.hutu.modules.admin.followup.service.impl;

import com.hutu.common.entity.R;
import com.hutu.modules.admin.followup.vo.AdminSolutionNodeItemFrom;
import com.hutu.modules.admin.followup.vo.AdminSolutionNodeItemVo;
import com.hutu.modules.common.entity.SolutionNodeItem;
import com.hutu.modules.admin.followup.mapper.AdminSolutionNodeItemMapper;
import com.hutu.modules.admin.followup.service.AdminSolutionNodeItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.common.entity.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 方案节点子表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Service
public class AdminSolutionNodeItemServiceImpl extends ServiceImpl<AdminSolutionNodeItemMapper, SolutionNodeItem> implements AdminSolutionNodeItemService {


    @Autowired
    private AdminTemplateServiceImpl adminTemplateService;

    @Autowired
    private AdminSolutionNodeItemMapper mapper;

    /**
     * 这里需要做一个表单内容的copy
     *
     * @param data
     * @return
     */
    @Override
    public R saveOrUpdateContainsTemplate(SolutionNodeItem data) {
        Template template = null;
        if (data.getTemplateId() != null) {
            template = adminTemplateService.getById(data.getTemplateId());
        }
        if (template != null) {
            String htmlContent = template.getHtmlContent();
//            data.setContent(htmlContent);
        }
        return this.saveOrUpdate(data) ? R.ok() : R.error("保存失败");
    }

    @Override
    public List<AdminSolutionNodeItemVo> getOneByDate(AdminSolutionNodeItemFrom from) {
        return mapper.getOneByDate(from);
    }
}
