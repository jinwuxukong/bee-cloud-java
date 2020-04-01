package com.hutu.modules.app.followup.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.followup.entity.AppSolutionNodeItemVo;
import com.hutu.modules.app.followup.entity.SolutionNodeItemFrom;
import com.hutu.modules.app.followup.mapper.AppSolutionNodeItemMapper;
import com.hutu.modules.app.followup.service.AppSolutionNodeItemService;
import com.hutu.modules.common.entity.SolutionNodeItem;
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
public class AppSolutionNodeItemServiceImpl extends ServiceImpl<AppSolutionNodeItemMapper, SolutionNodeItem> implements AppSolutionNodeItemService {

    @Autowired
    private AppSolutionNodeItemMapper mapper;

    @Override
    public List<SolutionNodeItem> getList(SolutionNodeItem data) {
        return mapper.getList(data);
    }

    @Override
    public AppSolutionNodeItemVo getOneById(String id) {
        return mapper.getOneById(id);
    }

    @Override
    public List<AppSolutionNodeItemVo> getOneByDate(SolutionNodeItemFrom from) {
        return mapper.getOneByDate(from);
    }
}
