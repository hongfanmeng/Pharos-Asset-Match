package com.zhd.algorithm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhd.dao.AssetMatchDao;
import com.zhd.dao.DeviceDao;
import com.zhd.dao.TaskDao;
import com.zhd.entity.AssetDevice;
import com.zhd.entity.Task;
import com.zhd.ws.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskProcessor {
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private AssetMatchDao assetMatchDao;
    @Autowired
    private WebSocketServer webSocketServer;


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

    public  void execute(AssetDevice device,Task task,int period) throws InterruptedException {
        task.setStatus(2);
        taskDao.updateTask(task);
        // 执行  用休眠模拟
        String track = deviceDao.getTrack(device.getDeviceId());
        double [] d=new double[2];
        List<? extends double[]> trackData = JSONObject.parseArray(track, d.getClass());
        int size=trackData==null||trackData.isEmpty()?1:trackData.size();
        trackData.forEach((e)->{
            double[] location=e;
            try {
                Thread.sleep(5000L*period/size);
                device.setStatus(1);
                device.setLongitude(location[0]);
                device.setLatitude(location[1]);
                deviceDao.updateDevice(device);
                System.out.println(5000L*period/trackData.size()+"】"+device.getDeviceName()+" move to "+location[0]+","+location[1]);
                webSocketServer.sendDeviceChange(device);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
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
