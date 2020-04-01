package com.hutu.modules.app.wx.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.app.wx.mapper.WxPatientMapper;
import com.hutu.modules.app.wx.service.WxPatientService;
import com.hutu.modules.app.wx.vo.WxPatientFollowupRecordVo;
import com.hutu.modules.common.entity.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 医生患者信息表 服务实现类
 * @author hutu
 * @date 2019-12-31 17:00
 */
@Service
public class WxPatientServiceImpl extends ServiceImpl<WxPatientMapper, Patient> implements WxPatientService {

    @Override
    public IPage<WxPatientFollowupRecordVo> getPatientFollowupRecord(Page page, Integer patientId, String keyWord,Integer status) {
        return baseMapper.getPatientFollowupRecord(page, patientId, keyWord,status);
    }
}
