package com.hutu.modules.admin.followup.mapper;

import com.hutu.modules.common.entity.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2020-03-17
 */
@Mapper
public interface AdminLoginLogMapper extends BaseMapper<LoginLog> {

    List<LoginLog> getList(@Param("date") LoginLog date);
}