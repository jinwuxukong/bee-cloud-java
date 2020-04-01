package com.hutu.modules.admin.followup.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hutu.modules.admin.upms.entity.User;
import com.hutu.modules.common.entity.Doctor;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class AdminDoctorVo {

    private Doctor doctor;

    private User user;

    private int age;
}
