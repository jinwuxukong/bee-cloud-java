package com.hutu.modules.common.service;

import com.hutu.common.entity.R;

public interface SmsService {
    /**
     * 验证码验证
     *
     * @param phone
     * @return
     */
    R sendSms(String phone);

    R checkCode(String phone, String code);

    R doctorInform(String[] phone, String name);
}
