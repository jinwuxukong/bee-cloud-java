package com.hutu.modules.app.wx.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.modules.app.wx.service.WxPatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信患者-随访记录
 *
 * @author hutu
 * @date 2019-12-30 18:54
 */
@Api(tags = "微信患者-随访记录")
@RestController
@RequestMapping("wxFollowupRecord")
public class WxFollowupRecordController {

    @Autowired
    WxPatientService wxPatientService;

    @AuthIgnore
    @ApiOperation("通过患者id获取随访记录列表")
    @GetMapping("/getFollowupRecord/{current}/{pageSize}/{patientId}")
    public R getDefaultTeamInfo(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                                @ApiParam("患者id") @PathVariable("patientId") Integer patientId, @ApiParam("搜索关键字") String keyWord,@ApiParam("状态")Integer status) {

        return R.ok().put("list", wxPatientService.getPatientFollowupRecord(new Page(current, pageSize), patientId, keyWord,status).getRecords());
    }

}

