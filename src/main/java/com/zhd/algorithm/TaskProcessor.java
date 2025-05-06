package com.zhd.algorithm;

import com.zhd.dao.AssetMatchDao;
import com.zhd.dao.DeviceDao;
import com.zhd.dao.TaskDao;
import com.zhd.entity.AssetDevice;
import com.zhd.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskProcessor {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private AssetMatchDao assetMatchDao;
    public  AssetDevice fetchDevice(Task task) throws InterruptedException {
        task.setStatus(1);
        taskDao.insertTask(task);
        // 分配Device
        AssetDevice assetDevice = deviceDao.selectOneValidDevice(task);
        System.out.println("fetchDevice:"+assetDevice);
        if(assetDevice==null){
            // 重复尝试获取资源，直到失败
            for(int i=0;i<100;i++){
                Thread.sleep(2000L);
                assetDevice = deviceDao.selectOneValidDevice(task);
            }
            if(assetDevice==null){
                exit(task);
                return null;
            }
        }
        assetDevice.setStatus(1);
        assetMatchDao.match(assetDevice,task);
        deviceDao.updateDevice(assetDevice);
        return assetDevice;
    }

    public  void execute(Task task,int period) throws InterruptedException {
        task.setStatus(2);
        taskDao.updateTask(task);
        // TODO 执行  用休眠模拟
        Thread.sleep(period*1000);
        System.out.println("执行任务完毕:"+task);
    }

    public  void finish(Task task,AssetDevice assetDevice){
        task.setStatus(3);
        taskDao.updateTask(task);
        // 释放Device
        assetDevice.setStatus(0);
        deviceDao.updateDevice(assetDevice);
    }

    public void exit(Task task){
        task.setStatus(4);
        taskDao.updateTask(task);
    }
}
