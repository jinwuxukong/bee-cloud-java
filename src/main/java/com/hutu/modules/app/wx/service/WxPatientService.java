package com.hutu.modules.app.wx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.wx.vo.WxPatientFollowupRecordVo;
import com.hutu.modules.common.entity.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
/**
 * 微信患者端-患者相关接口
 * @author hutu
 * @date 2019-12-31 16:59
 */
public interface WxPatientService extends IService<Patient> {

    IPage<WxPatientFollowupRecordVo> getPatientFollowupRecord(Page page,Integer patientId, String keyWord,Integer status);
}
