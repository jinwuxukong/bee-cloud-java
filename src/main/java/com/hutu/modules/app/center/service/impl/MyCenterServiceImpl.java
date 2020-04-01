package com.hutu.modules.app.center.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hutu.common.entity.R;
import com.hutu.common.util.JwtUtils;
import com.hutu.modules.admin.upms.entity.User;
import com.hutu.modules.admin.upms.service.UserService;
import com.hutu.modules.app.center.service.AppDoctorService;
import com.hutu.modules.app.center.service.MyCenterService;
import com.hutu.modules.app.center.entity.StatisticsScaleVo;
import com.hutu.modules.app.center.entity.UserVo;
import com.hutu.modules.app.center.service.ScaleTypeService;
import com.hutu.modules.app.followup.service.*;
import com.hutu.modules.common.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 个人中心
 */
@Service
public class MyCenterServiceImpl implements MyCenterService {
    @Autowired
    private AppDoctorService appDoctorService;
    @Autowired
    private UserService userService;
    @Autowired
    private ScaleTypeService scaleTypeService;


    @Value("${web.url-path}")
    private String urlPath;


    @Override
    public UserVo getMyInfo(Integer id) {
        UserVo userInfo = new UserVo();
        Doctor doctor = appDoctorService.getOne(new QueryWrapper<Doctor>().eq("userId", id));
        userInfo.setDoctor(doctor);
        if (id != null) {
            User user = userService.getOne(new QueryWrapper<User>().eq("id", id));
            //拼接头像
            if(user!=null&&StrUtil.isNotEmpty(user.getAvatar())){
                String[] tmp = urlPathSplice(user.getAvatar());
                user.setAvatar(tmp[0]);
                if (StrUtil.isNotEmpty(user.getAvatar()) && !StrUtil.startWith(user.getAvatar(), "http")) {
                    user.setAvatar(urlPath + user.getAvatar());
                }
            }
            BeanUtils.copyProperties(user,userInfo);
        }
        return userInfo;
    }

    /**
     * 我的中心首页
     *
     * @param userId
     * @return
     */
    @Override
    public UserVo getShowInfo(Integer userId) {
        return this.getMyInfo(userId);
    }


    /**
     * 个人信息更新
     *
     * @param data
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(UserVo data) {
        Integer userId = JwtUtils.getUserId();
        UpdateWrapper<Doctor> doctorUpdateWrapper = new UpdateWrapper<>();
        doctorUpdateWrapper.eq("userId",userId);
        if(data.getDoctor()!=null){
            appDoctorService.update(data.getDoctor(),doctorUpdateWrapper);
        }
        User user = new User();
        BeanUtils.copyProperties(data,user);
        if (user!=null){
            return userService.update(user,new UpdateWrapper<User>().eq("id",userId));
        }else{
            return false;
        }
    }

    /**
     * 更换手机号
     *
     * @param pass
     * @param newPhone
     * @return
     */
    @Override
    public R changePhoneNumber(String pass, String newPhone) {
        Integer userId = JwtUtils.getUserId();

        //验证密码
        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        if(user.getName()==newPhone){
            return R.error("手机号已存在");
        }
        if (!user.getPass().equals(pass)) {
            return R.error("密码错误");
        }
        //更换手机号
        user.setPhone(newPhone).setName(newPhone);
        return userService.updateUser(user) ? R.ok("更换成功") : R.error("更换失败");
    }

    /**
     * 更换密码
     *
     * @param oldPass
     * @param newPass
     * @return
     */
    @Override
    public R changePass(String oldPass, String newPass) {
        Integer userId = JwtUtils.getUserId();
        //验证密码
        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        if (!user.getPass().equals(oldPass)) {
            return R.error("密码错误");
        }
        //更换密码
        user.setPass(newPass);
        return userService.updateUser(user) ? R.ok("更换成功") : R.error("更换失败");
    }

    @Override
    public StatisticsScaleVo statisticsScale(Integer userId) {
        return scaleTypeService.statisticsScale(userId);
    }

    @Override
    public StatisticsScaleVo nowStatisticsScale(Integer userId) {
        return scaleTypeService.nowStatisticsScale(userId);
    }

    @Override
    public R verifyPass(String pass) {
        Integer userId = JwtUtils.getUserId();
        User pass1 = userService.getOne(new QueryWrapper<User>().eq("pass", pass).eq("id",userId));
        if(pass1!=null){
            return R.ok();
        }else{
            return R.error("密码错误");
        }
    }

    @Override
    public R forgetPass(String pass,String Phone) {
        boolean update = userService.update(new UpdateWrapper<User>().set("pass", pass).eq("phone", Phone));
        if(update){
            return R.ok();
        }else{
            return R.error("该用户不存在");
        }
    }

    /**
     * 拼接路径
     *
     * @param originUrl
     * @return
     */
    private String[] urlPathSplice(String... originUrl) {
        Arrays.stream(originUrl).forEach(url -> {
            if (StrUtil.isNotEmpty(url) && !StrUtil.startWith(url, "http")) {
                url = urlPath + url;
            }
        });
        return originUrl;
    }


}
