package com.hutu.modules.admin.followup.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.common.util.YimiUtil;
import com.hutu.modules.admin.followup.service.AdminSolutionNodeItemService;
import com.hutu.modules.admin.followup.service.AdminSolutionNodeService;
import com.hutu.modules.admin.followup.vo.*;
import com.hutu.modules.app.followup.entity.AppSolutionNodeItemVo;
import com.hutu.modules.app.followup.entity.SolutionNodeItemFrom;
import com.hutu.modules.app.followup.entity.SolutionNodeVo;
import com.hutu.modules.app.followup.entity.SolutionVo;
import com.hutu.modules.common.entity.Solution;
import com.hutu.modules.admin.followup.mapper.AdminSolutionMapper;
import com.hutu.modules.admin.followup.service.AdminSolutionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.common.entity.SolutionNode;
import com.hutu.modules.common.entity.SolutionNodeItem;
import com.hutu.modules.common.entity.Template;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 方案表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Service
public class AdminSolutionServiceImpl extends ServiceImpl<AdminSolutionMapper, Solution> implements AdminSolutionService {

    @Autowired
    private AdminSolutionNodeService adminSolutionNodeService;
    @Autowired
    private AdminSolutionNodeItemService adminSolutionNodeItemService;
    @Autowired
    private AdminSolutionMapper adminSolutionMapper;

    @Override
    public R createOrUpdate(Solution data) {
        data.setScope(2);
        data.setScopeShow("平台");
        if (data.getId() == null) {
            //设置解决方案的编号
            data.setSolutionNumber("FA-" + YimiUtil.getNumber("solutionNumber"));
            return save(data) ? R.ok().put("id", data.getId()) : R.error();
        }
        return updateById(data) ? R.ok().put("id", data.getId()) : R.error();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized R create(SolutionFrom data) {
        if(data.getSolution().getScope()==null){
            if(JwtUtils.getCallerInfo().type==3||JwtUtils.getCallerInfo().type==4){
                data.getSolution().setScope(0);
                data.getSolution().setScopeShow("个人");
                data.getSolution().setVerifyId(JwtUtils.getUserId());
                data.getSolution().setVerifyName(JwtUtils.getUserName());
                data.getSolution().setVerifyTime(LocalDateTime.now());
                data.getSolution().setVerifyStatus(1);
            }else{
                data.getSolution().setScope(2);
                data.getSolution().setScopeShow("平台");
            }
        }
        //设置版本号
        if(data.getSolution().getSolutionNumber()!=null){
            int version = adminSolutionMapper.getVersion(data.getSolution().getSolutionNumber());
            data.getSolution().setVersion(version+1);
            this.update(new UpdateWrapper<Solution>().set("status", 0).eq("solutionNumber", data.getSolution().getSolutionNumber()));
        }else{
            data.getSolution().setVersion(1);
        }
        //若是管理员，那么就将模板scope的状态置为2
        if(JwtUtils.getCallerInfo().type.equals(0)){
            data.getSolution().setVerifyId(JwtUtils.getUserId());
            data.getSolution().setVerifyName(JwtUtils.getUserName());
            data.getSolution().setVerifyTime(LocalDateTime.now());
            data.getSolution().setVerifyStatus(1);
        }
        if(data.getSolution().getSolutionNumber()==null){
            //设置解决方案的编号
            data.getSolution().setSolutionNumber("FA-" + YimiUtil.getNumber("solutionNumber"));
        }
        data.getSolution().setVisitNum(data.getSolutionNodeList().size());
        //预计天数
        int num = 0;
        List<SolutionNodeItem> solutionNodeItems = new ArrayList<>();
        List<SolutionNode> solutionNodes = new ArrayList<>();
        if(data.getSolutionNodeList().size()>0){
            for (int i = 0; i < data.getSolutionNodeList().size(); i++) {
                SolutionNodeFrom solutionNodeFrom = data.getSolutionNodeList().get(i);
                if(solutionNodeFrom.getUnit()==1){
                    solutionNodeFrom.setIntervalDay(solutionNodeFrom.getIntervalValue());
                    solutionNodeFrom.setUnitShow("天");
                }else if(solutionNodeFrom.getUnit()==2){
                    solutionNodeFrom.setIntervalDay(solutionNodeFrom.getIntervalValue()*7);
                    solutionNodeFrom.setUnitShow("周");
                }else if(solutionNodeFrom.getUnit()==3){
                    solutionNodeFrom.setIntervalDay(solutionNodeFrom.getIntervalValue()*30);
                    solutionNodeFrom.setUnitShow("月");
                }else if(solutionNodeFrom.getUnit()==4){
                    solutionNodeFrom.setIntervalDay(solutionNodeFrom.getIntervalValue()*365);
                    solutionNodeFrom.setUnitShow("年");
                }
                solutionNodeFrom.setOrders(i+1);
                //预计天数计算
                num+= solutionNodeFrom.getIntervalDay();
            }
            data.getSolution().setPredictDay(num);
            save(data.getSolution());
            for (SolutionNodeFrom solutionNodeFrom : data.getSolutionNodeList()) {
                SolutionNode solutionNode = new SolutionNode();
                BeanUtils.copyProperties(solutionNodeFrom,solutionNode);
                solutionNode.setSolutionId(data.getSolution().getId());
                solutionNode.setLbNum(solutionNodeFrom.getFormSolutionNodeItemList().size());
                solutionNode.setXjNum(solutionNodeFrom.getVisitSolutionNodeItemList().size());
                adminSolutionNodeService.save(solutionNode);
                solutionNodes.add(solutionNode);
                if(solutionNodeFrom.getFormSolutionNodeItemList().size()>0){
                    for (SolutionNodeItem solutionNodeItem : solutionNodeFrom.getFormSolutionNodeItemList()) {
                        solutionNodeItem.setSolutionNodeId(solutionNode.getId());
                        solutionNodeItem.setName(solutionNodeItem.getTemplateName());
                        solutionNodeItems.add(solutionNodeItem);
                    }
                }
                if(solutionNodeFrom.getVisitSolutionNodeItemList().size()>0){
                    for (SolutionNodeItem solutionNodeItem : solutionNodeFrom.getVisitSolutionNodeItemList()) {
                        solutionNodeItem.setSolutionNodeId(solutionNode.getId());
                        solutionNodeItem.setName(solutionNodeItem.getTemplateName());
                        solutionNodeItems.add(solutionNodeItem);
                    }
                }
            }
        }
        if(solutionNodeItems.size()>0){
            adminSolutionNodeItemService.saveBatch(solutionNodeItems);
        }
        return R.ok();
    }

    @Override
    public SolutionFrom descById(Integer id) {
        SolutionFrom solutionFrom = new SolutionFrom();
        solutionFrom.setSolution(new Solution());
        //获取方案复制到solutionFrom的方案
        BeanUtils.copyProperties(getById(id),solutionFrom.getSolution());
        List<SolutionNode> solutionNode = adminSolutionNodeService.list(new QueryWrapper<SolutionNode>().eq("solutionId", solutionFrom.getSolution().getId()));
        List<SolutionNodeFrom> solutionNodeFromList = new ArrayList<>();
        //遍历方案节点
        for (SolutionNode node : solutionNode) {
            SolutionNodeFrom solutionNodeFrom = new SolutionNodeFrom();
            //复制节点信息
            BeanUtils.copyProperties(node,solutionNodeFrom);
            List<SolutionNodeItem> solutionNodeItem = adminSolutionNodeItemService.list(new QueryWrapper<SolutionNodeItem>().eq("solutionNodeId", node.getId()));
            List<SolutionNodeItem> visitSolutionNodeItemList = new ArrayList<>();
            List<SolutionNodeItem> formSolutionNodeItemList = new ArrayList<>();
            //遍历方案子节点分类表单和宣教
            for (SolutionNodeItem nodeItem : solutionNodeItem) {
                if(nodeItem.getTemplateType()==1){
                    formSolutionNodeItemList.add(nodeItem);
                }else{
                    visitSolutionNodeItemList.add(nodeItem);
                }
            }
            solutionNodeFrom.setVisitSolutionNodeItemList(visitSolutionNodeItemList);
            solutionNodeFrom.setFormSolutionNodeItemList(formSolutionNodeItemList);
            solutionNodeFromList.add(solutionNodeFrom);
        }
        solutionFrom.setSolutionNodeList(solutionNodeFromList);
        return solutionFrom;
    }

    @Override
    public List<Solution> getList(Solution data) {
        return adminSolutionMapper.getList(data);
    }

    @Override
    public IPage<Solution> getPage(Page objectPage, Map<String, Object> params) {
        return adminSolutionMapper.getPage(objectPage,params);
    }

    @Override
    public List<Solution> getVersionList(Integer id, String solutionNumber) {
        return adminSolutionMapper.getVersionList(id,solutionNumber);
    }

}
