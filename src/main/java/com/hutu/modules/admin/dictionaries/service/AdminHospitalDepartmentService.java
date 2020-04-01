package com.hutu.modules.admin.dictionaries.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.admin.dictionaries.entity.AdminHospitalDepartmentVo;
import com.hutu.modules.common.entity.HospitalDepartment;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医院科室表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-12-05
 */
public interface AdminHospitalDepartmentService extends IService<HospitalDepartment> {

    IPage<AdminHospitalDepartmentVo> pageVo(Page objectPage, Map<String, Object> params);

    List<HospitalDepartment> binaryTree();
}
