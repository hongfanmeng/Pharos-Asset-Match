package com.zhd.service.impl;


import com.alibaba.fastjson.JSON;
import com.zhd.constant.ConstantValue;
import com.zhd.dao.TaskDao;
import com.zhd.entity.flow.Requirement;
import com.zhd.entity.flow.WorkFlow;
import com.zhd.service.MatchService;
import com.zhd.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private ConstantValue constantValue;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private AsyncRestTemplate asyncRestTemplate;
    @Autowired
    private MatchService matchService;

    @Override
    public void saveRequirement(Requirement requirement) {
        taskDao.insertRequirement(requirement);
    }

    @Override
    public List<Requirement> getAllRequirements() {
        return taskDao.getAllRequirements();
    }

    @Override
    public WorkFlow generateWorkFlow(Requirement requirement)  {
        // 调用任务流模块，根据需求生成任务流
        String url = constantValue.flowUrl+ "/requirement";

        // 准备请求体
        String requestParam= JSON.toJSONString(requirement);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestParam, headers);

        // 发起异步POST请求
        ListenableFuture<ResponseEntity<WorkFlow>> future =
                asyncRestTemplate.postForEntity(url, requestEntity, WorkFlow.class);

        // 设置回调处理
        future.addCallback(
                result -> {
                    matchService.executeFlow(result.getBody());
                },
                ex -> {
                    // 错误处理
                    System.err.println("Error: " + ex.getMessage());
                }
        );
        WorkFlow workFlow=null;
        try{
            ResponseEntity<WorkFlow> response = future.get();
            workFlow=response.getBody();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return workFlow;
    }
}
