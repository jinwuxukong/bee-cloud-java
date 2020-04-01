package com.hutu.modules.app.followup.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.modules.app.followup.entity.SolutionNodeItemFrom;
import com.hutu.modules.app.followup.service.AppSolutionNodeItemService;
import com.hutu.modules.common.entity.SolutionNodeItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

@Api(tags = "App-方案节点子节点")
@RestController
@RequestMapping("AppFollowupSolutionNodeItem")
public class AppSolutionNodeItemController {
    @Autowired
    private AppSolutionNodeItemService appSolutionNodeItemService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<SolutionNodeItem> queryWrapper = new QueryWrapper<>();
        Page<SolutionNodeItem> page = new Page<>(current, pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        appSolutionNodeItemService.page(page, queryWrapper);
        return R.ok().put("list", page.getRecords()).put("total", page.getTotal());
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") SolutionNodeItem data) {
        return appSolutionNodeItemService.saveOrUpdate(data) ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return appSolutionNodeItemService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @AuthIgnore
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",appSolutionNodeItemService.getOneById(id));
    }

    @ApiOperation("通过ID获取一条数据")
    @AuthIgnore
    @GetMapping("/getOneByDate")
    public R getOneByDate(@RequestBody @ApiParam("数据对象") SolutionNodeItemFrom from){
        return R.ok().put("info",appSolutionNodeItemService.getOneByDate(from));
    }

    @ApiOperation("通过对象获取列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") SolutionNodeItem data){
        return R.ok().put("info",appSolutionNodeItemService.getList(data));
    }

}
