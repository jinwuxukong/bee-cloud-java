package com.hutu.modules.admin.followup.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.entity.R;
import com.hutu.modules.admin.followup.service.AdminSolutionService;
import com.hutu.modules.common.entity.SolutionNode;
import com.hutu.modules.common.entity.SolutionNodeItem;
import com.hutu.modules.admin.followup.service.AdminSolutionNodeItemService;
import com.hutu.modules.admin.followup.service.AdminSolutionNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 方案节点表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-10-08
 */

@Api(tags = "随访运营端-方案节点")
@RestController
@RequestMapping("followupSolutionNode")
public class AdminSolutionNodeController {
    @Autowired
    private AdminSolutionService adminSolutionService;
    @Autowired
    private AdminSolutionNodeService adminSolutionNodeService;
    @Autowired
    private AdminSolutionNodeItemService adminSolutionNodeItemService;

     /*@ApiOperation("获取page")
     @GetMapping("/page/{current}/{pageSize}")
     public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                      @ApiParam("方案id") @RequestParam(required = false) Integer solutionId) {
         QueryWrapper<SolutionNode> queryWrapper = new QueryWrapper<>();
         Page<SolutionNode> page=new Page<>(current,pageSize);
         if (solutionId!=null) {
             queryWrapper.like("solutionId", solutionId);
         }
         solutionNodeService.page(page,queryWrapper);
         return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
     }*/

    @ApiOperation("日历分页")
    @GetMapping("/calendarPage/{current}/{solutionId}")
    public R calendarPage(@ApiParam("当前页") @PathVariable("current") Integer current, @ApiParam("方案id") @PathVariable("solutionId") Integer solutionId) {
        int maxResult = 28;
        int totalRecord = adminSolutionNodeService.totalRecordMax(solutionId);
        return R.ok().put("info", adminSolutionNodeService.list(new QueryWrapper<SolutionNode>().eq("solutionId",solutionId).between("intervalDay",current,(current * maxResult)))).put("total",totalRecord % maxResult == 0 ? totalRecord / maxResult : totalRecord / maxResult + 1);
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") SolutionNode data) {
        List<SolutionNode> solutionNode = adminSolutionNodeService.list(new QueryWrapper<SolutionNode>().eq("solutionId", data.getSolutionId()));
        if(solutionNode!=null){
            data.setOrders(solutionNode.size()+1);
        }else{
            data.setOrders(1);
        }
        return adminSolutionNodeService.createOrUpdateSolutionNode(data);
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return adminSolutionNodeService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") String id) {
        return R.ok().put("info", adminSolutionNodeService.getById(id));
    }

    @ApiOperation("获取方案下所有节点信息")
    @GetMapping("/getNodes/{id}")
    public R getNodes(@ApiParam("方案id") @PathVariable("id") Integer id) {
        return R.ok().put("info", adminSolutionNodeService.listNodes(id)).put("total",adminSolutionNodeService.totalRecordMax(id));
    }

    @ApiOperation("根据节点id获取获取列表项")
    @GetMapping("/getItems/{id}")
    public R getItems(@ApiParam("节点id") @PathVariable("id") Integer id) {
        QueryWrapper<SolutionNodeItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("solutionNodeId", id);
        return R.ok().put("list", adminSolutionNodeItemService.list(queryWrapper));
    }

}
