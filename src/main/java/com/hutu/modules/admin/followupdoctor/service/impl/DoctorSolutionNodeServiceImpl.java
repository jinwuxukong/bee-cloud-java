package com.hutu.modules.admin.followupdoctor.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hutu.common.entity.R;
import com.hutu.modules.common.entity.SolutionNode;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.admin.followupdoctor.mapper.DoctorSolutionNodeMapper;
import com.hutu.modules.admin.followupdoctor.service.DoctorSolutionNodeService;
import com.hutu.modules.admin.followupdoctor.service.DoctorSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class DoctorSolutionNodeServiceImpl extends ServiceImpl<DoctorSolutionNodeMapper, SolutionNode> implements DoctorSolutionNodeService {
    @Autowired
    private DoctorSolutionService doctorSolutionService;

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


    /*private List<AdminSolutionNodeVo> compute(List<SolutionNode> list, Solution solution) {
        List<AdminSolutionNodeVo> lists = new ArrayList<>();
        LocalDateTime createTime = solution.getCreateTime();
        //转化为dateTime类型
        String s = createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DateTime dateTime = DateUtil.parseDateTime(s);
        list.forEach(solutionNode -> {
            AdminSolutionNodeVo solutionNodeVo = new AdminSolutionNodeVo();
            BeanUtils.copyProperties(solutionNode, solutionNodeVo);
            //设置据创建时间的偏移天数//初始为0
            if (solutionNode.getUnit() == 1) {
                DateTime newDateTime = dateTime.offset(DateField.DAY_OF_YEAR, solutionNode.getIntervalValue());
                newDateTime.between(dateTime, DateUnit.DAY);
                System.out.println(newDateTime.between(dateTime, DateUnit.DAY));
                //solutionNodeVo.setDay(dateTime.between(newDateTime, DateUnit.DAY));
            }
            //表示为周
            if (solutionNode.getUnit() == 2) {
                dateTime.offset(DateField.DAY_OF_WEEK, solutionNode.getIntervalValue());
                solutionNodeVo.setDay(dateTime.between(dateTime, DateUnit.WEEK));
            }
        });
        return null;
    }*/

    public static void main(String[] args) {
        DateTime dateTime = new DateTime();
        dateTime.offset(DateField.DAY_OF_YEAR, 6);
        long between = dateTime.between(new Date(), DateUnit.DAY);
        System.out.println(between);
        /*if (solutionNode.getUnit() == 1) {

            }
            //表示为周
            if (solutionNode.getUnit() == 2) {

            }
            //月
            if (solutionNode.getUnit() == 3) {

            }
            //年
            if (solutionNode.getUnit() == 4) {

            }*/
    }
}
