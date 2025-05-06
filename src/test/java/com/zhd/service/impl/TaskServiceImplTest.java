package com.zhd.service.impl;

import com.zhd.dao.TaskDao;
import com.zhd.entity.Task;
import com.zhd.entity.flow.Requirement;
import com.zhd.entity.flow.WorkFlow;
import com.zhd.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TaskServiceImplTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskDao taskDao;

    @Test
     void daoTest(){
        for(int i=0;i<3;i++){
            Task task = new Task();
            task.setTaskName("taskNavigate");
            task.setLeasingCompany("C"+i);
            task.setRequirementId("id01");
            task.setCapId(3L);
            task.setLongitude(100.0);
            task.setLatitude(100.0);
            task.setStatus(1);

            taskDao.insertTask(task);
            System.out.println(task);
        }

    }

    @Test
    void daoThreadTest(){
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for(int i=0;i<3;i++){
            int ii=i;
            Thread t=new Thread(()->{
                Task task = new Task();
                task.setTaskName("taskNavigate");
                task.setLeasingCompany("C"+ii);
                task.setRequirementId("id01");
                task.setCapId(3L);
                task.setLongitude(100.0);
                task.setLatitude(100.0);
                task.setStatus(1);
                taskDao.insertTask(task);
                System.out.println(task);
            });
            threadPool.execute(t);
        }
        threadPool.shutdown();
    }

    @Test
    void saveRequirement() {
        taskService.saveRequirement(
                new Requirement(
                        "id01",
                        "需求标题",
                        "需求描述",
                        100.0,
                        102.0,
                        "XXX有限责任公司",
                        null
                )
        );
    }

    @Test
    void getAllRequirements() {
        List<Requirement> allRequirements = taskService.getAllRequirements();
        allRequirements.forEach(System.out::println);
    }

    @Test
    void generateWorkFlow() {
        List<Requirement> allRequirements = taskService.getAllRequirements();
        WorkFlow workFlow = taskService.generateWorkFlow(allRequirements.get(0));
        System.out.println(workFlow);
    }
}