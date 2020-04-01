package com.hutu.modules.admin.followup.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.common.constant.Constant;
import com.hutu.modules.admin.followup.vo.DoctorVo;
import com.hutu.modules.common.entity.Doctor;
import com.hutu.modules.common.entity.GlobalConfig;
import com.hutu.modules.admin.followup.form.DoctorRegisterForm;
import com.hutu.modules.admin.followup.mapper.AdminDoctorMapper;
import com.hutu.modules.admin.followup.service.AdminDoctorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hutu.modules.admin.followup.service.AdminGlobalConfigService;
import com.hutu.modules.admin.upms.entity.User;
import com.hutu.modules.admin.upms.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 医生信息表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-09-23
 */
@Service(value = "adminDoctorServiceImpl")
public class AdminDoctorServiceImpl extends ServiceImpl<AdminDoctorMapper, Doctor> implements AdminDoctorService {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminDoctorMapper adminDoctorMapper;

    @Autowired
    private AdminGlobalConfigService adminGlobalConfigService;

    /**
     * 每天的访问人数,每24小时重置一次
     */
    private static Integer number = 1;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean register(DoctorRegisterForm data) {

        User user = new User();
        BeanUtils.copyProperties(data, user);
        //设置为3
        user.setType(Constant.YIMI_DOCTOR_TYPE)
                .setTypeShow("随访医生")
                .setName(data.getPhone());
        boolean b1 = userService.saveUser(user);

        Integer userId = user.getId();

        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(data, doctor);
        doctor.setUserId(userId);
        if (StrUtil.isNotEmpty(getYimiNumber())) {
            doctor.setYimiNumber(getYimiNumber());
        } else {
            try {
                throw new Exception("依米医生注册失败！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        boolean b2 = save(doctor);
        return b1 && b2;
    }

    @Override
    public IPage<DoctorVo> getPage(Page objectPage, Map<String, Object> params) {
        return adminDoctorMapper.getPage(objectPage,params);
    }

    /**
     * 生成依米id
     *
     * @return
     */
    private String getYimiNumber() {
        GlobalConfig one = adminGlobalConfigService.getOne(new QueryWrapper<GlobalConfig>().eq("code", "yimiNumber"));
        Integer version = one.getVersion();
        String value = one.getValue();
        if (StrUtil.isEmpty(value)) {
            //初始化操作
            value = "201909230001";
        }
        //字符串拼接
        value = getValue(value);

        boolean flag = adminGlobalConfigService.update(new UpdateWrapper<GlobalConfig>()
                .set("value", value)
                .set("version", version + 1)
                .eq("code", "yimiNumber")
                .eq("version", version));
        return flag ? value : "";
    }

    private String getValue(String value) {
        if (value.length() != 12) {
            return value;
        }

        String dateNowStr = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        //判断是今天吗
        String numberStr = "";
        if (!value.substring(0, 8).equals(dateNowStr)) {
            //从1开始
            numberStr = new DecimalFormat("0000").format(1);
            return dateNowStr + numberStr;
        }

        numberStr = new DecimalFormat("0000").format(Integer.parseInt(value.substring(value.length() - 4, value.length())) + 1);
        value = dateNowStr + numberStr;
        return value;
    }
}
