package com.zhd.dao.impl;

import com.zhd.dao.TaskDao;
import com.zhd.entity.Task;
import com.zhd.entity.flow.Requirement;
import com.zhd.mapper.RequirementMapper;
import com.zhd.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private RequirementMapper requirementMapper;

    @Override
    public Task insertTask(Task task) {
        System.out.println("is inserted?"+task);
        try{
            int insert = taskMapper.insert(task);
            System.out.println("inserted task:"+task+","+insert);
        }catch (Exception e){
            e.printStackTrace();
        }

        return task;
    }

    @Override
    public void updateTask(Task task) {
        taskMapper.updateById(task);
    }

    @Override
    public void insertRequirement(Requirement requirement) {
        requirementMapper.insert(requirement);
    }

    @Override
    public List<Requirement> getAllRequirements() {
        return requirementMapper.selectAllWithTasks();
    }
}
