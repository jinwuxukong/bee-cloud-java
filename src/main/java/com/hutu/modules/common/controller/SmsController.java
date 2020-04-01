package com.hutu.modules.common.controller;

import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.common.util.PatternUtils;
import com.hutu.modules.common.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户校验
 */
@Api(tags = "验证码")
@RestController
@RequestMapping("validate")
public class SmsController {

    @Autowired
    private SmsService smsService;

    /**
     * 发送验证码
     *
     * @return
     */
    @ApiOperation("发送验证码")
    @AuthIgnore
    @GetMapping("/send/{phone}")
    public R sendSms(@ApiParam("手机号") @PathVariable("phone") String phone) {
        if (!PatternUtils.isPhone(phone)) {
            return R.error("手机号不正确");
        }
        return smsService.sendSms(phone);
    }


    @ApiOperation("验证验证码的正确")
    @AuthIgnore
    @GetMapping("/check")
    public R checkCode(@ApiParam("手机号") @RequestParam("phone") String phone, @ApiParam("验证码") @RequestParam("code") String code) {
        return smsService.checkCode(phone, code);
    }

    @ApiOperation("医生通知患者填写表单")
    @AuthIgnore
    @GetMapping("/doctorInform")
    public R doctorInform(@ApiParam("手机号") @RequestParam("phone") String phone) {
        return smsService.doctorInform(phone.split(","), JwtUtils.getUserName());
    }
}
