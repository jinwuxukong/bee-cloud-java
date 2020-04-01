package com.hutu.modules.app.center.controller;

import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.admin.followup.service.AdminGlobalConfigService;
import com.hutu.modules.admin.upms.service.UserService;
import com.hutu.modules.app.center.entity.UserVo;
import com.hutu.modules.app.center.service.MyCenterService;
import com.hutu.modules.app.followup.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@Api(tags = "APP-个人中心")
@RestController
@RequestMapping("/myCenter/")
public class MyCenterController {
    @Value("${web.url-path}")
    private String urlPath;
    @Autowired
    private MyCenterService myCenterService;
    @Autowired
    private AdminGlobalConfigService globalConfigService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserService UserService;

    @ApiOperation("我的中心首页")
    @GetMapping("show")
    public R show() {
        return R.ok().put("info", myCenterService.getShowInfo(JwtUtils.getUserId()));
    }

    @ApiOperation("通过id查看个人信息")
    @GetMapping("read/{id}")
    public R read(@ApiParam("id") @PathVariable("id") Integer id) {
        return R.ok().put("info", myCenterService.getMyInfo(id));
    }

    @ApiOperation("修改个人信息")
    @PostMapping("update")
    public R update(@RequestBody @ApiParam("数据对象") UserVo data) {
        return myCenterService.update(data) ? R.ok() : R.error("更新错误");
    }

    @ApiOperation("统计患者")
    @GetMapping("/statisticsPatient")
    public R statisticsPatient(@ApiParam("统计天数") @RequestParam("dayNum") int dayNum) {
        return R.ok().put("list", patientService.statisticsPatient(JwtUtils.getUserId(),dayNum));
    }

    @ApiOperation("统计量表数量")
    @GetMapping("/statisticsScale")
    public R statisticsScale() {
        return R.ok().put("list", myCenterService.statisticsScale(JwtUtils.getUserId()));
    }

    @ApiOperation("统计今日量表数量")
    @GetMapping("/nowStatisticsScale")
    public R nowStatisticsScale() {
        return R.ok().put("list", myCenterService.nowStatisticsScale(JwtUtils.getUserId()));
    }

    @ApiOperation("关于我们")
    @GetMapping("/aboutUs")
    public R aboutUs(@ApiParam("栏目的key") @RequestParam("key") String key) {
        return R.ok().put("list", globalConfigService.getValueByKey(key));
    }

    @ApiOperation("更换手机号")
    @GetMapping("/changePhone")
    public R changePhoneNumber(
            @ApiParam("密码") @RequestParam("pass") String pass,
            @ApiParam("新手机号") @RequestParam("newPhone") String newPhone) {
        return myCenterService.changePhoneNumber(pass, newPhone);
    }

    @ApiOperation("更换密码")
    @GetMapping("/changePass")
    public R changePass(
            @ApiParam("旧密码") @RequestParam("oldPass") String oldPass,
            @ApiParam("新密码") @RequestParam("newPass") String newPass) {
        return myCenterService.changePass(oldPass, newPass);
    }

    @ApiOperation("验证密码")
    @GetMapping("/verifyPass")
    public R verifyPass(
            @ApiParam("旧密码") @RequestParam("Pass") String Pass) {
        return myCenterService.verifyPass(Pass);
    }

    @ApiOperation("忘记密码")
    @GetMapping("/forgetPass")
    @AuthIgnore
    public R forgetPass(
            @ApiParam("新密码") @RequestParam("pass") String pass,@ApiParam("手机号")@RequestParam("phone") String phone) {
        return myCenterService.forgetPass(pass,phone);
    }
}

