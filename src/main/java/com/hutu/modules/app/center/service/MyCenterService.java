package com.hutu.modules.app.center.service;

import com.hutu.common.entity.R;
import com.hutu.modules.app.center.entity.StatisticsScaleVo;
import com.hutu.modules.app.center.entity.UserVo;

/**
 * 个人中心
 */
public interface MyCenterService {

    UserVo getMyInfo(Integer userId);

    boolean update(UserVo data);

    UserVo getShowInfo(Integer userId);

    R changePhoneNumber(String password, String newPhone);

    R changePass(String oldPass, String newPass);

    StatisticsScaleVo statisticsScale(Integer userId);

    StatisticsScaleVo nowStatisticsScale(Integer userId);

    R verifyPass(String pass);

    R forgetPass(String pass, String Phone);
}
