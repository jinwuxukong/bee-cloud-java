package com.hutu.modules.admin.followup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.common.entity.SolutionNodeItem;
import com.hutu.modules.admin.followup.service.AdminSolutionNodeItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 方案节点子表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */

@Api(tags = "随访运营端-方案节点")
@RestController
@RequestMapping("followupSolutionNodeItem")
public class AdminSolutionNodeItemController {
    @Autowired
    private AdminSolutionNodeItemService adminSolutionNodeItemService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<SolutionNodeItem> queryWrapper = new QueryWrapper<>();
        Page<SolutionNodeItem> page = new Page<>(current, pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        adminSolutionNodeItemService.page(page, queryWrapper);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") SolutionNodeItem data) {
        return adminSolutionNodeItemService.saveOrUpdateContainsTemplate(data);
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return adminSolutionNodeItemService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",adminSolutionNodeItemService.getById(id));
    }

}
