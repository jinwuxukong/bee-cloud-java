package com.hutu.modules.admin.followup.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.annotation.AuthIgnore;
import com.hutu.common.entity.R;
import com.hutu.modules.admin.followup.vo.AdminDoctorVo;
import com.hutu.modules.admin.followup.vo.AdminStatisticsVo;
import com.hutu.modules.admin.followup.vo.DoctorVo;
import com.hutu.modules.admin.upms.entity.User;
import com.hutu.modules.admin.upms.service.UserService;
import com.hutu.modules.common.entity.Doctor;
import com.hutu.modules.admin.followup.service.AdminDoctorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 医生信息表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */

@Api(tags = "随访运营端-医生管理")
@RestController
@RequestMapping("adminDoctor")
public class AdminDoctorController {
    @Autowired
    @Qualifier("adminDoctorServiceImpl")
    private AdminDoctorService adminDoctorService;

    @Autowired
    private UserService userService;

    /**
     * 获取医生的信息
     *
     * @param current
     * @param pageSize
     * @return
     */
    @ApiOperation("获取page")
    @AuthIgnore
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页") @PathVariable("current") int current, @ApiParam("分页大小") @PathVariable("pageSize") int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord,
                     @ApiParam("状态") @RequestParam(required = false) Integer verifyStatus) {
        Map<String, Object> params = new HashMap<String, Object>();
        if(StrUtil.isNotEmpty(keyWord)){
            params.put("nick",keyWord);
        }
        if(StrUtil.isNotEmpty(keyWord)){
            params.put("phone",keyWord);
        }
        if(StrUtil.isNotEmpty(keyWord)){
            params.put("doctorHospital",keyWord);
        }
        if(verifyStatus!=null){
            params.put("verifyStatus",verifyStatus);
        }
        IPage<DoctorVo> page = adminDoctorService.getPage(new Page<>(current,pageSize),params);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }

    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象") Doctor data) {
        return adminDoctorService.saveOrUpdate(data) ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("设置名医")
    @GetMapping("/famousDoctor/{id}/{type}")
    public R famousDoctor(@ApiParam("医生id") @PathVariable("id") Integer id, @ApiParam("名医类型") @PathVariable("type")Integer type) {
        return adminDoctorService.update(new UpdateWrapper<Doctor>().set("famousDoctor",type).eq("userId",id)) ? R.ok() : R.error("保存错误");
    }

    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合") @PathVariable("ids") String ids) {
        return adminDoctorService.removeByIds(StrUtil.split(ids, ',')) ? R.ok() : R.error("删除错误");
    }

    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id") @PathVariable("id") String id) {
        return R.ok().put("info", adminDoctorService.getById(id));
    }

    @ApiOperation("医生所有详情查看")
    @GetMapping("/doctorCertified/{id}")
    public R doctorCertified(@ApiParam("数据对象id") @PathVariable("id") String id) {
        AdminDoctorVo adminDoctorVo = new AdminDoctorVo();
        User user = userService.getById(id);
        Doctor Doctor = adminDoctorService.getOne(new QueryWrapper<Doctor>().eq("userId",user.getId()));
        adminDoctorVo.setUser(user);
        adminDoctorVo.setDoctor(Doctor);
        if(user.getBirthday()!=null){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthday = user.getBirthday();
            adminDoctorVo.setAge(DateUtil.ageOfNow(birthday.format(fmt)));
        }
        return R.ok().put("info", adminDoctorVo);
    }

    @ApiOperation("拒绝理由")
    @GetMapping("/reason/{id}/{reason}")
    public R reason(@ApiParam("数据对象id")@PathVariable("id") String id,@ApiParam("拒绝理由") @PathVariable("reason") String reason) {
        return R.ok().put("info", adminDoctorService.update(new UpdateWrapper<Doctor>().set("reason",reason).set("verifyStatus",0).eq("id",id)));
    }

    @ApiOperation("统计")
    @GetMapping("/statistics")
    public R statistics() {
        AdminStatisticsVo adminStatisticsVo = new AdminStatisticsVo();
        adminStatisticsVo.setCertifiedPhysician(adminDoctorService.count(new QueryWrapper<Doctor>().eq("verifyStatus",0)));
        adminStatisticsVo.setFamousDoctorNum(adminDoctorService.count(new QueryWrapper<Doctor>().eq("famousDoctor",1).eq("verifyStatus",1))+"/"+adminDoctorService.count(new QueryWrapper<Doctor>().eq("verifyStatus",1)));
        adminStatisticsVo.setLogoutDoctor(0);
        return R.ok().put("info",adminStatisticsVo);
    }

    @ApiOperation("待认证医生")
    @GetMapping("/certifiedPhysician")
    public R certifiedPhysician() {
        return R.ok().put("info",adminDoctorService.count(new QueryWrapper<Doctor>().eq("verifyStatus",0)));
    }

    @ApiOperation("名医/医生数量")
    @GetMapping("/famousDoctorNum")
    public R famousDoctorNum() {
        return R.ok().put("info",adminDoctorService.count(new QueryWrapper<Doctor>().eq("famousDoctor",1).eq("verifyStatus",1))+"/"+adminDoctorService.count(new QueryWrapper<Doctor>().eq("verifyStatus",1)));
    }

    @ApiOperation("注销医生")
    @GetMapping("/logoutDoctor")
    public R logoutDoctor() {
        return R.ok().put("info",0);
    }

}
