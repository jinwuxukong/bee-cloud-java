package com.hutu.modules.common.controller;

import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.config.OssConfig;
import com.hutu.config.QiniuConfig;
import com.hutu.modules.common.dto.OssCallbackResult;
import com.hutu.modules.common.dto.OssPolicyResult;
import com.hutu.modules.common.service.impl.OssServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.util.UUID;

@RestController
@RequestMapping("/oss")
@Api(tags = "文件上传")
public class OssController {

    @Autowired
    private QiniuConfig qiniuUtil;

    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "请选择文件";
        }
        try {
            FileInputStream fileInputStream = (FileInputStream) file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String fileExtend = originalFilename.substring(originalFilename.lastIndexOf("."));
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String fileKey = UUID.randomUUID().toString().replace("-", "") + fileExtend;
            return qiniuUtil.upload(fileInputStream, fileKey);
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    /*@GetMapping("/uptoken")
    public R getUptoken() {
        String accessKey = qiniuUtil.getAccessKey();
        String secretKey = qiniuUtil.getSecretKey();
        String bucket = qiniuUtil.getBucketName();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);
        return R.ok().put("uptoken", upToken);
    }*/
    /**
     * 阿里云oss相关接口
     */
    @Autowired
    private OssServiceImpl ossService;

    @ApiOperation(value = "oss上传签名生成")
    @AuthIgnore
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    public R policy() {
        OssPolicyResult result = ossService.policy();
        return R.ok().put("result",result);
    }

    @ApiOperation(value = "oss上传成功回调")
    @AuthIgnore
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    public OssCallbackResult callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        System.out.println(ossCallbackResult);
        return ossCallbackResult;
    }
}
