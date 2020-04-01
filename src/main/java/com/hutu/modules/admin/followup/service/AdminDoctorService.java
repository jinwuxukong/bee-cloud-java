package com.hutu.modules.admin.followup.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.admin.followup.vo.DoctorVo;
import com.hutu.modules.common.entity.Doctor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.admin.followup.form.DoctorRegisterForm;

import java.util.Map;

/**
 * <p>
 * 医生信息表 服务类
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */
public interface AdminDoctorService extends IService<Doctor> {

    /**
     * 随访的医生登录
     *
     * @param data
     * @return
     */
    boolean register(DoctorRegisterForm data);

    /**
     *
     * @param objectPage
     * @param params
     * @return
     */
    IPage<DoctorVo> getPage(Page objectPage, Map<String, Object> params);

}
