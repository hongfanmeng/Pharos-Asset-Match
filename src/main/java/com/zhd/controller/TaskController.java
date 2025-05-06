package com.zhd.controller;

import com.zhd.entity.flow.Requirement;
import com.zhd.entity.flow.SequenceFlow;
import com.zhd.entity.flow.WorkFlow;
import com.zhd.service.MatchService;
import com.zhd.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private MatchService matchService;
    @Autowired
    private TaskService taskService;

    /**
     *  ====== 前端接口 =======
     */
    /**
     * 前端发布需求
     * @param requirement
     */
    @PostMapping("/requirement")
    public void postRequirement(@RequestBody Requirement requirement){
        // 1. 接收需求并存储
        taskService.saveRequirement(requirement);
        // 2. 调用任务流模块生成子任务集合
        taskService.generateWorkFlow(requirement);

    }

    @GetMapping("/requirements")
    public List<Requirement> getAllRequirements(){
        // 获取所有需求下的所有子任务
        return taskService.getAllRequirements();
    }

    /**
     * ======= 任务流模块接口 =======
     */

    /**
     *  接收任务流并调用
     * @param workFlow
     */
//    @PostMapping("/flow")
//    public void recvFlows(@RequestBody WorkFlow workFlow){
//
//        // 1. 接收WorkFLow，并存储
//
//        // 2. 任务和设备，按照能力匹配
//        matchService.executeFlow(workFlow);
//    }

}
