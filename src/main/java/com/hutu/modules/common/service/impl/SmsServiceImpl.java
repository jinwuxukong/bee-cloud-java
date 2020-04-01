package com.hutu.modules.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aliyuncs.exceptions.ClientException;
import com.hutu.common.entity.R;
import com.hutu.common.util.AliyunSmsUtils;
import com.hutu.common.util.CacheUtils;
import com.hutu.modules.common.service.SmsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 首页模块-活动表 服务实现类
 * </p>
 *
 * @author generator
 * @since 2019-08-20
 */
@Service
public class SmsServiceImpl implements SmsService {

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @Override
    public R sendSms(String phone) {
        Integer code = (int) ((Math.random() * 9 + 1) * 100000);
        String json = "{\"code\":\"" + code + "\"}";
        try {
            boolean isSend = AliyunSmsUtils.sendSms(phone, json, AliyunSmsUtils.verificationCode);
            if (isSend) {
                CacheUtils.put(phone, StrUtil.toString(code));
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return R.ok().put("msg", "发送成功");
    }

    /**
     * 验证手机的验证码正确，或者超时
     *
     * @param phone
     * @param code
     * @return
     */
    @Override
    public R checkCode(String phone, String code) {
        String tmpCode = CacheUtils.get(phone);
        if (tmpCode == null) {
            return R.error("验证码已经超时");
        }
        if (StrUtil.isEmpty(tmpCode) || !tmpCode.equals(code)) {
            return R.error("验证码错误");
        }
        return R.ok().put("msg", "验证码正确");
    }

    @Override
    public R doctorInform(String[] phone, String name) {
        String json = "{\"dortorName\":\"" + name + "\"}";
        try {
            for (String i : phone) {
                boolean b = AliyunSmsUtils.sendSms(i, json, AliyunSmsUtils.remind);
                if(!b){
                    return R.error("短信发送失败");
                }
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return R.error("短信发送失败");
        }
        return R.ok();
    }
}
