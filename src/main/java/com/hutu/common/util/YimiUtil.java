package com.hutu.common.util;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hutu.modules.admin.followup.service.AdminGlobalConfigService;
import com.hutu.modules.common.entity.GlobalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * 依米生成工具类
 *
 * @author zhangyunze
 * @date 2019-10-14
 */
@Component
public class YimiUtil {

    @Autowired
    private AdminGlobalConfigService globalConfigService;

    private static YimiUtil yimiUtil;

    @PostConstruct
    public void init() {
        yimiUtil = this;
        yimiUtil.globalConfigService = this.globalConfigService;
    }

    /**
     * 生成12位数日期数字 201909230001 的编码
     *
     * @return
     */
    public static String getNumber(String code) {
        GlobalConfig one = yimiUtil.globalConfigService.getOne(new QueryWrapper<GlobalConfig>().eq("code", code));
        Integer version = one.getVersion();
        String value = one.getValue();
        if (StrUtil.isEmpty(value)) {
            //初始化
            value = LocalDate.now().toString().replace("-","");
        }
        value = getValue(value);
        boolean flag = yimiUtil.globalConfigService.update(new UpdateWrapper<GlobalConfig>()
                .set("value", value)
                .set("version", version + 1)
                .eq("code", code)
                .eq("version", version));
        return flag ? value : "";
    }

    /**
     * 日期字符串处理
     *
     * @param value
     * @return
     */
    private static String getValue(String value) {
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


    /**
     * 依米Id
     * @return
     */
    public static String getYimiNumber() {
        //基数10000000
        GlobalConfig one = yimiUtil.globalConfigService.getOne(new QueryWrapper<GlobalConfig>().eq("code", "yimiNumber"));
        Integer version = one.getVersion();
        String value = one.getValue();
        if (StrUtil.isEmpty(value)) {
            value = "10000000";
        }
        boolean flag = yimiUtil.globalConfigService.update(new UpdateWrapper<GlobalConfig>()
                .set("value", Long.valueOf(value)+1)
                .set("version", version + 1)
                .eq("code", "yimiNumber")
                .eq("version", version));
        return flag ? value : "";
    }
}