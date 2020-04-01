package com.hutu.modules.app.wx.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.app.wx.vo.WxPatientFollowupRecordVo;
import com.hutu.modules.common.entity.Patient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 患者信息表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-12-27
 */
@Mapper
public interface WxPatientMapper extends BaseMapper<Patient> {

    IPage<WxPatientFollowupRecordVo> getPatientFollowupRecord(Page page, @Param("patientId") Integer patientId, @Param("keyWord")String keyWord,@Param("status")Integer status);
}