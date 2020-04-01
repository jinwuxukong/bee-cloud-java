package com.hutu.modules.admin.dictionaries.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.admin.dictionaries.entity.AdminHospitalDepartmentVo;
import com.hutu.modules.admin.dictionaries.mapper.AdminHospitalDepartmentMapper;
import com.hutu.modules.admin.dictionaries.service.AdminHospitalDepartmentService;
import com.hutu.modules.common.entity.HospitalDepartment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医院科室表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-12-05
 */
@Service
public class AdminHospitalDepartmentServiceImpl extends ServiceImpl<AdminHospitalDepartmentMapper, HospitalDepartment> implements AdminHospitalDepartmentService {

    @Autowired
    private AdminHospitalDepartmentMapper mapper;

    @Override
    public IPage<AdminHospitalDepartmentVo> pageVo(Page objectPage, Map<String, Object> params) {
        return mapper.pageVo(objectPage,params);
    }

    @Override
    public List<HospitalDepartment> binaryTree() {
        List<HospitalDepartment> HospitalDepartment = this.list();
        List<AdminHospitalDepartmentVo> hospitalDepartmentVos = new ArrayList<>();
        List<HospitalDepartment> HospitalDepartmentList = new ArrayList<>();
        for (HospitalDepartment hospitalDepartment : HospitalDepartment) {
            if(hospitalDepartment.getType()==1){
                AdminHospitalDepartmentVo hospitalDepartmentVo = new AdminHospitalDepartmentVo();
                hospitalDepartmentVo.setChildren(new ArrayList<HospitalDepartment>());
                BeanUtils.copyProperties(hospitalDepartment,hospitalDepartmentVo);
                hospitalDepartmentVos.add(hospitalDepartmentVo);
            }
        }
        for (AdminHospitalDepartmentVo hospitalDepartmentVo : hospitalDepartmentVos) {
            for (HospitalDepartment hospitalDepartment : HospitalDepartment) {
                if(hospitalDepartmentVo.getId()==hospitalDepartment.getParentId()){
                    hospitalDepartmentVo.getChildren().add(hospitalDepartment);
                }
            }
        }
        for (AdminHospitalDepartmentVo item : hospitalDepartmentVos) {
            if(item.getChildren().size()<1){
                HospitalDepartment hospitalDepartment = new HospitalDepartment();
                BeanUtils.copyProperties(item,hospitalDepartment);
                HospitalDepartmentList.add(hospitalDepartment);
            }else{
                for (HospitalDepartment child : item.getChildren()) {
                    HospitalDepartmentList.add(child);
                }
            }
        }
        return HospitalDepartmentList;
    }
}
