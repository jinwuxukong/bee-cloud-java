package com.hutu.modules.admin.upms.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.common.util.UUIDUtils;
import com.hutu.modules.admin.upms.entity.Attachment;
import com.hutu.modules.admin.upms.service.AttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * <p>
 * 附件表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-08-16
 */

@Api(tags = "附件表")
@RestController
@RequestMapping("attachment")
public class AttachmentController {

    @Value("${web.upload-path}")
    private String filePath;

    @Value("${web.url-path}")
    private String urlPath;
    @Autowired
    private AttachmentService attachmentService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Attachment> queryWrapper = new QueryWrapper<>();
        Page<Attachment> page = new Page<>(current, pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        attachmentService.page(page, queryWrapper);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") Attachment data) {
        return attachmentService.saveOrUpdate(data) ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return attachmentService.removeByIds(StrUtil.split(ids,',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") String id) {
        return R.ok().put("info", attachmentService.getById(id));
    }

    @ApiOperation("公共文件上传接口")
    @AuthIgnore
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file,@RequestParam(required = false) Integer type) {
        //新文件子目录
        String strPath = DateTime.now().toString(DatePattern.PURE_DATE_PATTERN) + "/";

        //原文件名称
        String fileName = file.getOriginalFilename();

        //新文件名称
        assert fileName != null;
        String newFileName = UUIDUtils.generateShortUuid() + fileName.substring(fileName.lastIndexOf("."));

        String attachPath = strPath + newFileName;

        File newFile = new File(filePath + strPath);

        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        try {
            file.transferTo(new File(filePath + strPath + newFileName));

            Attachment attachment = new Attachment();
            attachment.setFileName(fileName);
            attachment.setPath(attachPath);
            attachment.setType(type);
            attachmentService.save(attachment);
            return R.ok().put("fileInfo", attachment);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("上传失败");
        }
    }

}
