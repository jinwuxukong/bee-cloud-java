package com.hutu.modules.app.wx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.wx.mapper.WxDoctorMapper;
import com.hutu.modules.app.wx.service.WxDoctorService;
import com.hutu.modules.app.wx.vo.WxDoctorInfoVo;
import com.hutu.modules.common.entity.Doctor;
import org.springframework.stereotype.Service;

@Service
public class WxDoctorServiceImpl extends ServiceImpl<WxDoctorMapper, Doctor> implements WxDoctorService {

    @Override
    public WxDoctorInfoVo getDoctorInfoByUserId(Integer id) {
        return baseMapper.getDoctorInfoByUserId(id);
    }
}
