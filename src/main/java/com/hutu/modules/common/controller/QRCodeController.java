package com.hutu.modules.common.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.hutu.common.annotation.AuthIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地址列表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-08-10
 */

@Api(tags = "二维码")
@RestController
@RequestMapping("qrcode")
public class QRCodeController {

    @ApiOperation("获取二维码")
    @AuthIgnore
    @GetMapping("/{code}")
    public void twoCode(@ApiParam("二维码编号") @PathVariable("code") String code, HttpServletResponse response) {
        try {
            byte[] bytes = QrCodeUtil.generatePng(code, 300, 300);
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
