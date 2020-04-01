package com.hutu.modules.admin.followupdoctor.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.admin.followupdoctor.service.DoctorSolutionNodeItemService;
import com.hutu.modules.admin.followupdoctor.service.DoctorSolutionNodeService;
import com.hutu.modules.admin.followupdoctor.service.DoctorTemplateService;
import com.hutu.modules.admin.followupdoctor.vo.DoctorSolutionVo;
import com.hutu.modules.common.entity.Solution;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.admin.followupdoctor.mapper.DoctorSolutionMapper;
import com.hutu.modules.admin.followupdoctor.service.DoctorSolutionService;
import com.hutu.modules.common.entity.SolutionNode;
import com.hutu.modules.common.entity.SolutionNodeItem;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 方案表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */
@Service
public class DoctorSolutionServiceImpl extends ServiceImpl<DoctorSolutionMapper, Solution> implements DoctorSolutionService {

    @Autowired
    private DoctorSolutionNodeService nodeService;

    @Autowired
    private DoctorSolutionNodeItemService itemService;

    @Autowired
    private DoctorTemplateService templateService;

    @Override
    public R createOrUpdate(Solution data) {
        if (data.getId() == null) {
            return save(data) ? R.ok().put("id", data.getId()) : R.error();
        }
        return updateById(data) ? R.ok().put("id", data.getId()) : R.error();
    }

    /**
     * 根据不同的条件筛选，并分页
     *
     * @param page
     * @param keyWord 搜索的关键字
     * @param status  禁用状态
     * @param types   类型的id,用 ','隔开
     * @return
     */
    @Override
    public IPage<Solution> getMyPage(Page<Solution> page, String keyWord, Integer status, String types) {
        QueryWrapper<Solution> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("currentOwnerId", JwtUtils.getUserId());
        String[] fields = {"id", "name", "type", "typeShow", "scope", "scopeShow", "scopeShow", "status", "createName", "createTime", "currentOwnerId", "currentOwnerName", "verifyStatus"};
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        if (StrUtil.isNotEmpty(types) && StrUtil.isNotBlank(types)) {
            queryWrapper.in("type", Arrays.asList(types.split(",")));
        }
        queryWrapper.select(fields).orderByDesc("createTime");
        return baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * 获取所有平台的方案
     *
     * @param page
     * @param keyWord 搜索的关键字
     * @param status  禁用状态
     * @param types   类型的id,用 ','隔开
     * @param scope   共享的范围
     * @return
     */
    @Override
    public IPage getPlateFormPage(Page page, String keyWord, Integer status, String types, Integer scope) {
        QueryWrapper<Solution> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        if (scope != null) {
            queryWrapper.eq("scope", scope);
        }
        if (StrUtil.isNotEmpty(types) && StrUtil.isNotBlank(types)) {
            queryWrapper.in("type", Arrays.asList(types.split(",")));
        }
        IPage<Solution> solutionIPage = baseMapper.selectPage(page, queryWrapper);
        List<Solution> records = solutionIPage.getRecords();
        //  查询当前医生的自己的方案
        List<Solution> ownSolutions = list(new QueryWrapper<Solution>().eq("currentOwnerId", JwtUtils.getUserId()));
        List<DoctorSolutionVo> doctorSolutionVos = new ArrayList<>();
        //这里做一个比较，拿到当前分页的10条数据，去遍历医生的所有方案
        for (Solution record : records) {
            DoctorSolutionVo doctorSolutionVo = new DoctorSolutionVo();
            BeanUtils.copyProperties(record, doctorSolutionVo);
            ownSolutions.stream().forEach(own -> {
                if (own.getSolutionNumber() != null) {
                    if (own.getSolutionNumber().equals(doctorSolutionVo.getSolutionNumber()) && own.getCreateId().equals(doctorSolutionVo.getCreateId())) {
                        doctorSolutionVo.setHadAdd(1);
                    }
                }
            });
            doctorSolutionVos.add(doctorSolutionVo);
        }
        return page.setRecords(doctorSolutionVos);
    }

    /**
     * 将平台方案设为自己的方案
     * 将所有的都copy出来
     *
     * @param solutionId 方案的id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addPlateFormSolutionToMe(int solutionId) {
        //1,通过solutionId 找到它自己的信息
        Solution originSolution = getById(solutionId);
        Solution newSolution = new Solution();
        BeanUtils.copyProperties(originSolution, newSolution);
        //1，1 重置id,范围等信息
        newSolution.setId(null)
                .setScope(0)
                .setScopeShow("个人")
                .setCreateTime(LocalDateTime.now())
                .setCurrentOwnerId(JwtUtils.getUserId())
//                .setCurrentOwnerTime(LocalDateTime.now())
                .setCurrentOwnerName(JwtUtils.getUserName());
        //1.2 执行插入
        boolean flag1 = save(newSolution);
        if (!flag1) {
            return R.error("方案添加失败");
        }
        //2,通过solutionId查找到它的子solutionNodes
        //2.1 获取最新生成的方案id，将新id替换旧的
        Integer newId = newSolution.getId();
        List<SolutionNode> nodes = nodeService.list(new QueryWrapper<SolutionNode>().eq("solutionId", solutionId));
        //先保存以前的nodes
        List<SolutionNode> originNodes = nodes;
        if (nodes == null) {
            //表示方案没有子节点，退出
            return R.ok();
        }
        List<Integer> nodeIds = new ArrayList<>();
        nodes.forEach(node -> {
            nodeIds.add(node.getId());
            node.setSolutionId(newId);
        });
        //2.2将nodes批量插入到数据库中
        boolean flag2 = nodeService.saveBatch(nodes);
        if (!flag2) {
            return R.error("节点添加失败");
        }
        //添加完毕之后 nodes 会改变
        List<SolutionNode> newNodes = nodes;
        //3,通过nodeId再找到items
        Map<Integer, List<SolutionNodeItem>> items = itemService
                .list(new QueryWrapper<SolutionNodeItem>().in("solutionNodeId", nodeIds))
                .stream().collect(Collectors.groupingBy(SolutionNodeItem::getSolutionNodeId));
        //新增的nodes id的集合
        Set<Integer> newIds = newNodes.stream().collect(Collectors.groupingBy(SolutionNode::getId)).keySet();
        Object[] newIdArray = newIds.toArray();
        //通过老的id去map中拿，拿到之后，把新的id赋值给他们
        //此时数量相等
        if (newIdArray.length == nodeIds.size()) {
            for (int i = 0; i < newIdArray.length; i++) {
                Integer j = (Integer) newIdArray[i];
                List<SolutionNodeItem> solutionNodeItems = items.get(nodeIds.get(i));
                if (solutionNodeItems != null) {
                    solutionNodeItems.forEach(tmp -> {
                        tmp.setSolutionNodeId(j);
                        tmp.setId(null);
                    });
                }
            }
        }
        //4,将items 批量插入
        List<SolutionNodeItem> newItems = new ArrayList<>();
        Collection<List<SolutionNodeItem>> values = items.values();
        values.forEach(newItems::addAll);
        boolean flag3 = itemService.saveBatch(newItems);
        if (!flag3) {
            return R.error();
        }
        return R.ok("成功添加到我的方案");
    }


//    public static void main(String[] args) {
//
//        List<Solution> list1 = new ArrayList<>();
//        List<Solution> list2 = new ArrayList<>();
//
//
//        Solution solution = new Solution();
//        solution.setSolutionNumber("1");
//
//        Solution solution1 = new Solution();
//        solution1.setSolutionNumber("2");
//
//        list1.add(solution);
//        list1.add(solution1);
//
//        Solution solution3 = new Solution();
//        solution3.setSolutionNumber("1");
//
//        Solution solution4 = new Solution();
//        solution4.setSolutionNumber("3");
//
//        list2.add(solution3);
//        list2.add(solution4);
//
//
////        list1.forEach(l1 -> list2.forEach(l2 -> {
////            if (l1.getSolutionNumber() == l2.getSolutionNumber()) {
////                System.out.println("l2 = " + l2);
////            }
////        }));
//
//
//        list1.stream()
//                .filter(l1 -> list2.stream().anyMatch(l2 -> l1.getSolutionNumber().equals(l2.getSolutionNumber())))
//                .forEach(l -> {
//                    System.out.println("l = " + l);
//                });
//    }
}
