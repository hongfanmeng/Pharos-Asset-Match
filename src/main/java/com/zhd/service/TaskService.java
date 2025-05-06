package com.zhd.service;

import com.zhd.entity.flow.Requirement;
import com.zhd.entity.flow.WorkFlow;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface TaskService {
    void saveRequirement(Requirement requirement);
    List<Requirement> getAllRequirements();

    /**
     * 向任务生成模块发起请求，获取WorkFlow数据
     * @param requirement
     * @return
     */
    WorkFlow generateWorkFlow(Requirement requirement);
}
