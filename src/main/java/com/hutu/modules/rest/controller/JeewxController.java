package com.hutu.modules.rest.controller;

import com.alibaba.fastjson.JSONObject;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.modules.rest.service.JeewxService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * 供捷微系统调用的接口
 * @author hutu
 * @date 2020-01-01 14:42
 */
@RestController
@RequestMapping("jeewx")
@Slf4j
public class JeewxController {

    @Autowired
    private JeewxService jeewxService;
    /**
     * 创建二维码url
     */
    @Value("${jeewx.qrcode.create}")
    private String url;

    /**
     * 调用微信系统生成二维码
     * 二维码参数 param1,param2(param1为医生id，param2为团队id) 例如：  666,2
     *
     * @return
     */
    @AuthIgnore
    @ApiOperation("创建微信二维码")
    @GetMapping("/createWxQrcode/{parameter}")
    public R createWxQrcode(@ApiParam("二维码参数") @PathVariable("parameter") String parameter) {
        RestTemplate template = new RestTemplate();
        String qrUrl = template.getForObject(url + parameter, String.class);
        log.info("===============生成二维码地址为: {}",qrUrl);
        return R.ok().put("imgUrl", qrUrl);
    }

    /**
     * 者关注了，但是立马取消订阅的时候，并没有绑定医生
     *
     * @param openId 患者微信id
     * @return 是否解绑成功
     */
    @AuthIgnore
    @GetMapping(value = "/delBindUnSubscribe")
    public boolean delBindUnSubscribe(@RequestParam("openId") String openId) throws Exception {
        return jeewxService.delBindUnSubscribe(openId);
    }

    /**
     * 处理公众号关注事件，通过扫码关注则绑定患者于团队和医生关系，若是非扫码关注则只新增一条患者信息
     *
     * @param openId 患者openId
     * @return 结果
     */
    @AuthIgnore
    @GetMapping(value = "/verifyPatientOpenId")
    public R verifyPatientOpenId(@RequestParam("openId") String openId, String parameter) {
        return R.ok().put("teamName",jeewxService.dealSubEvent(openId, parameter));
    }

}
