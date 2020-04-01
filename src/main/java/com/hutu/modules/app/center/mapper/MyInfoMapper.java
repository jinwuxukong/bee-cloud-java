package com.hutu.modules.app.center.mapper;

import com.hutu.modules.common.entity.MyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MyInfoMapper {

    MyInfo getMyInfo(@Param("id") Integer id);
}
