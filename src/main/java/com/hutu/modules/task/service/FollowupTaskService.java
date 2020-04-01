package com.hutu.modules.task.service;

public interface FollowupTaskService {

    boolean send() throws Exception;

    boolean timeout();
}
