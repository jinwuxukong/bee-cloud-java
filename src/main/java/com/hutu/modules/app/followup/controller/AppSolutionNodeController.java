package com.hutu.modules.app.followup.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hutu.common.entity.R;
import com.hutu.modules.app.followup.service.AppSolutionNodeItemService;
import com.hutu.modules.app.followup.service.AppSolutionNodeService;
import com.hutu.modules.common.entity.SolutionNode;
import com.hutu.modules.common.entity.SolutionNodeItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 方案节点表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */

@Api(tags = "App-方案节点")
@RestController
@RequestMapping("AppFollowupSolutionNode")
public class AppSolutionNodeController {
    @Autowired
    private AppSolutionNodeService appSolutionNodeService;
    @Autowired
    private AppSolutionNodeItemService appSolutionNodeItemService;

    /* @ApiOperation("获取page")
     @GetMapping("/page/{current}/{pageSize}")
     public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                      @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
         QueryWrapper<SolutionNode> queryWrapper = new QueryWrapper<>();
         Page<SolutionNode> page=new Page<>(current,pageSize);
         if (StrUtil.isNotEmpty(keyWord)) {
             queryWrapper.like("name", keyWord);
         }
         solutionNodeService.page(page,queryWrapper);
         return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
     }*/

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") SolutionNode data) {
        return appSolutionNodeService.createOrUpdateSolutionNode(data);
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return appSolutionNodeService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") String id) {
        return R.ok().put("info", appSolutionNodeService.getById(id));
    }

    @ApiOperation("获取方案下所有节点信息")
    @GetMapping("/getNodes/{id}")
    public R getNodes(@ApiParam("方案id") @PathVariable("id") Integer id) {
        return R.ok().put("list", appSolutionNodeService.listNodes(id));
    }

    @ApiOperation("根据节点id获取获取列表项")
    @GetMapping("/getItems/{id}")
    public R getItems(@ApiParam("节点id") @PathVariable("id") Integer id) {
        QueryWrapper<SolutionNodeItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("solutionNodeId", id);
        return R.ok().put("list", appSolutionNodeItemService.list(queryWrapper));
    }

    @ApiOperation("通过对象获取列表数据")
    @GetMapping("/getList")
    public R getList(@RequestBody @ApiParam("数据对象") SolutionNode data){
        return R.ok().put("info",appSolutionNodeService.getList(data));
    }
}
