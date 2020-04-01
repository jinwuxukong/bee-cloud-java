package com.hutu.modules.app.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hutu.modules.app.wx.vo.WxDoctorInfoVo;
import com.hutu.modules.common.entity.Doctor;

public interface WxDoctorService extends IService<Doctor> {
    WxDoctorInfoVo getDoctorInfoByUserId(Integer id);
}
