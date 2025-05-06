package com.zhd.dao;

import com.zhd.entity.Task;
import com.zhd.entity.flow.Requirement;

import java.util.List;

public interface TaskDao {
    Task insertTask(Task task);
    void updateTask(Task task);
    void insertRequirement(Requirement requirement);
    List<Requirement> getAllRequirements();
}
