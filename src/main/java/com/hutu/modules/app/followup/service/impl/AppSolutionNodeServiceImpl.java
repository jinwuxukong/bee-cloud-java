package com.hutu.modules.app.followup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.common.entity.R;
import com.hutu.modules.app.followup.mapper.AppSolutionNodeMapper;
import com.hutu.modules.app.followup.service.AppSolutionNodeService;
import com.hutu.modules.app.followup.service.AppSolutionService;
import com.hutu.modules.common.entity.SolutionNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 方案节点表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Service
public class AppSolutionNodeServiceImpl extends ServiceImpl<AppSolutionNodeMapper, SolutionNode> implements AppSolutionNodeService {
    @Autowired
    private AppSolutionService appSolutionService;
    @Autowired
    private AppSolutionNodeMapper mapper;

    @Override
    public List<SolutionNode> listNodes(Integer id) {
        QueryWrapper<SolutionNode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("solutionId", id).orderByAsc("intervalDay");
        return list(queryWrapper);
    }

    @Override
    public R createOrUpdateSolutionNode(SolutionNode solutionNode) {
        //在新建的时候，处理时间间隔
        if (solutionNode.getUnit() == 1) {
            solutionNode.setIntervalDay(solutionNode.getIntervalValue());
        }
        //表示为周
        if (solutionNode.getUnit() == 2) {
            solutionNode.setIntervalDay(solutionNode.getIntervalValue() * 7);
        }
        //月
        if (solutionNode.getUnit() == 3) {
            solutionNode.setIntervalDay(solutionNode.getIntervalValue() * 30);
        }
        //年
        if (solutionNode.getUnit() == 4) {
            solutionNode.setIntervalDay(solutionNode.getIntervalValue() * 365);
        }
        int intervalDay = solutionNode.getIntervalDay();
        //新增的时候校验，根据方案id和间隔天数
        if (solutionNode.getId() == null && intervalDay > 0) {
            QueryWrapper<SolutionNode> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("solutionId", solutionNode.getSolutionId()).eq("intervalDay", intervalDay);
            List<SolutionNode> list = list(queryWrapper);
            //找到
            if (list != null && list.size() > 0) {
                return R.error("天数重复");
            }
            return save(solutionNode) ? R.ok().put("id", solutionNode.getId()) : R.error("保存错误");
        }
        return updateById(solutionNode) ? R.ok() : R.error("更新错误");
    }

    @Override
    public List<SolutionNode> getList(SolutionNode data) {
        return mapper.getList(data);
    }
}
