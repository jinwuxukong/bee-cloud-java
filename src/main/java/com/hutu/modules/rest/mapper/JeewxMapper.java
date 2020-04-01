package com.hutu.modules.rest.mapper;

import com.hutu.modules.rest.vo.DoctorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author hutu
 * @date 2020-01-02 15:13
 */
@Mapper
public interface JeewxMapper {

    /**
     * 获取医生信息
     *
     * @param id 医生的用户表id
     * @return 医生信息
     */
    DoctorInfo getDoctorInfo(@Param("id") Integer id);
}