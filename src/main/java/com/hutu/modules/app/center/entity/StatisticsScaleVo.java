package com.hutu.modules.app.center.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 统计患者
 * </p>
 *
 * @author generator
 * @since 2019-09-28
 */
@Data
@Accessors(chain = true)
public class StatisticsScaleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 待完成
     */
    private int NoFinish;

    /**
     * 已收到
     */
    private int Finish;

    /**
     * 超时
     */
    private int overTime;

}
