package com.hutu.modules.app.followup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.common.entity.R;
import com.hutu.modules.app.followup.entity.AppSolutionNodeItemVo;
import com.hutu.modules.app.followup.entity.SolutionNodeItemFrom;
import com.hutu.modules.app.followup.entity.SolutionNodeVo;
import com.hutu.modules.app.followup.entity.SolutionVo;
import com.hutu.modules.app.followup.mapper.AppSolutionMapper;
import com.hutu.modules.app.followup.service.AppSolutionNodeItemService;
import com.hutu.modules.app.followup.service.AppSolutionNodeService;
import com.hutu.modules.app.followup.service.AppSolutionService;
import com.hutu.modules.common.entity.Solution;
import com.hutu.modules.common.entity.SolutionNode;
import com.hutu.modules.common.entity.SolutionNodeItem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 方案表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Service
public class AppSolutionServiceImpl extends ServiceImpl<AppSolutionMapper, Solution> implements AppSolutionService {

    @Autowired
    private AppSolutionNodeService appSolutionNodeService;
    @Autowired
    private AppSolutionNodeItemService appSolutionNodeItemService;
    @Autowired
    private AppSolutionMapper mapper;

    @Override
    public R createOrUpdate(Solution data) {
        if (data.getId() == null) {
            return save(data) ? R.ok().put("id", data.getId()) : R.error();
        }
        return updateById(data) ? R.ok().put("id", data.getId()) : R.error();
    }

    @Override
    public SolutionVo readById(Integer id) {
        SolutionVo solutionVo = new SolutionVo();
        Solution solution = this.getById(id);
        if(solution!=null){
            BeanUtils.copyProperties(solution,solutionVo);
            List<SolutionNode> solutionNode = appSolutionNodeService.list(new QueryWrapper<SolutionNode>().eq("solutionId", solution.getId()));
            ArrayList<SolutionNodeVo> solutionNodeVos = new ArrayList<>();
            int dayNum = 0;
            for (SolutionNode node : solutionNode) {
                dayNum+=node.getIntervalDay();
                SolutionNodeVo solutionNodeVo = new SolutionNodeVo();
                BeanUtils.copyProperties(node,solutionNodeVo);
                List<AppSolutionNodeItemVo> solutionNodeItem = appSolutionNodeItemService.getOneByDate(new SolutionNodeItemFrom().setSolutionNodeId(node.getId()));
                List<AppSolutionNodeItemVo> FormSolutionNodeItemList = new ArrayList<>();
                List<AppSolutionNodeItemVo> VisitSolutionNodeItemList = new ArrayList<>();
                for (AppSolutionNodeItemVo nodeItem : solutionNodeItem) {
                    if(nodeItem.getTemplateType()==1){
                        AppSolutionNodeItemVo AppSolutionNodeItemVo = new AppSolutionNodeItemVo();
                        BeanUtils.copyProperties(nodeItem,AppSolutionNodeItemVo);
                        FormSolutionNodeItemList.add(AppSolutionNodeItemVo);
                    }else{
                        AppSolutionNodeItemVo AppSolutionNodeItemVo = new AppSolutionNodeItemVo();
                        BeanUtils.copyProperties(nodeItem,AppSolutionNodeItemVo);
                        VisitSolutionNodeItemList.add(AppSolutionNodeItemVo);
                    }
                }
                solutionNodeVo.setFormSolutionNodeItemList(FormSolutionNodeItemList);
                solutionNodeVo.setVisitSolutionNodeItemList(VisitSolutionNodeItemList);
                solutionNodeVos.add(solutionNodeVo);
            }
            solutionVo.setPredictDay(dayNum);
            solutionVo.setVisitNum(solutionNode.size());
            solutionVo.setSolutionNodeVoLists(solutionNodeVos);
        }
        return solutionVo;
    }

    @Override
    public Integer countStatistics(Integer userId, int scope) {
        return mapper.countStatistics(userId,scope);
    }

    @Override
    public List<Solution> getList(Solution data) {
        return mapper.getList(data);
    }
}
