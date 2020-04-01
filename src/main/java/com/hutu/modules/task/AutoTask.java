package com.hutu.modules.task;

import com.hutu.modules.task.service.FollowupTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 简单定时任务
 *
 * @author hutu
 * @date 2019/9/28 10:52
 */
@EnableScheduling
@Component
@Slf4j
public class AutoTask {

    @Autowired
    private FollowupTaskService followUpTaskService;

    /**
     * 定时为患者发送表单
     * 每天 凌晨2点去扫描当天要去推送的任务
     */
    @Scheduled(cron = "0 0 2 * * ?")
    private void sendFollowupFormToPatient() throws Exception {
        log.info("===================定时给患者发送表单 开始===================");
        if (followUpTaskService.send()) {
            log.info("===================定时给患者发送表单 成功===================");
        }else{
            log.info("===================定时给患者发送表单 失败===================");
        }
    }

    /**
     * 定时查看表单是否超时3天未填写
     */
    @Scheduled(cron = "0 0 2 * * ?")
    private void sendFollowupFormToPatientTimeout() {
        log.info("===================定时查看表单是否超时未填写===================");
        if (followUpTaskService.timeout()) {
            log.info("===================查看表单超时任务 结束===================");
        }else{
            log.info("===================查看表单超时任务 失败===================");
        }

    }
}
