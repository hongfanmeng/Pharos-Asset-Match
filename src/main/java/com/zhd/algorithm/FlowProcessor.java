package com.zhd.algorithm;

import com.zhd.dao.AssetMatchDao;
import com.zhd.dao.DeviceDao;
import com.zhd.dao.TaskDao;
import com.zhd.entity.AssetDevice;
import com.zhd.entity.Task;
import com.zhd.entity.flow.Event;
import com.zhd.entity.flow.MetaTask;
import com.zhd.entity.flow.SequenceFlow;
import com.zhd.entity.flow.WorkFlow;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class FlowProcessor {
    @Autowired
    private TaskProcessor taskProcessor;
    @Autowired
    private TaskDao taskDao;

//    private static final Object lock=new Object();

    /**
     * 构建图
     * @param workFlow
     * @return
     */
    public  Map<String, List<SequenceFlow>>[] generateGraph(WorkFlow workFlow){
        // TODO 深搜寻路到终点，路上每个结点开个线程请求设备
        HashMap<String, List<SequenceFlow>> G=new HashMap<>();
        G.put(workFlow.getStartEvent().getId(),new ArrayList<>());
        G.put(workFlow.getEndEvent().getId(),new ArrayList<>());
        HashMap<String, List<SequenceFlow>> G2=new HashMap<>();
        G2.put(workFlow.getStartEvent().getId(),new ArrayList<>());
        G2.put(workFlow.getEndEvent().getId(),new ArrayList<>());
        // 边 s->t，存s的邻接表
        for(SequenceFlow sequenceFlow: workFlow.getSequenceFlows()){
            List<SequenceFlow> list;
            String sourceRef=sequenceFlow.getSourceRef();
            if(G.containsKey(sourceRef))
                list=G.get(sourceRef);
            else list=new ArrayList<>();
            list.add(sequenceFlow);
            G.put(sourceRef,list);

            List<SequenceFlow> list2;
            String targetRef=sequenceFlow.getTargetRef();
            if(G2.containsKey(targetRef))
                list2=G2.get(targetRef);
            else list2=new ArrayList<>();
            list2.add(sequenceFlow);
            G2.put(targetRef,list2);
        }
        return new HashMap[]{G,G2};
    }

    /**
     * 线程池并行处理工作流子任务
     * @param workFlow
     * @param G
     * @param parallel
     */
    public  void parallelExecute(WorkFlow workFlow, Map<String, List<SequenceFlow>>[] G, int parallel){
        ExecutorService threadPool = Executors.newFixedThreadPool(parallel);
        Event startEvent = workFlow.getStartEvent();
        LinkedList<String> list=new LinkedList<>();
        list.addLast(startEvent.getId());
        Map<String,Thread> threadMap=new HashMap<>();
        threadMap.put(startEvent.getId(), new Thread(()->{
            System.out.println("Start Event: "+startEvent.getName());
        }));
        Map<String, MetaTask> metaTaskMap=new HashMap<>();
        for(MetaTask metaTask: workFlow.getTasks()) metaTaskMap.put(metaTask.getId(),metaTask);
        while(!list.isEmpty()){
            String sourceRef = list.removeFirst();
            System.out.println(sourceRef);
            if(sourceRef.equals(workFlow.getEndEvent().getId())) break;
            Thread sourceThread=threadMap.get(sourceRef);
            List<SequenceFlow> sequenceFlows = G[0].get(sourceRef);
            for(SequenceFlow sequenceFlow:sequenceFlows){
                String targetRef=sequenceFlow.getTargetRef();
                Thread t=new Thread(()->{
                    MetaTask metaTask = metaTaskMap.get(targetRef);
                    Task task = MetaTask.toTask(metaTask,sequenceFlow);
                    try {
                        //  join T2/T3 -> T4
//                        synchronized (lock) {
                            // TODO 优化线程逻辑，有一条通路即可，不需要全部走完
                            sourceThread.join();
                            System.out.println("Start Sequence Flow: " + sequenceFlow);
                            Integer period = sequenceFlow.getPeriod();
                            if (period == null) period = 0;
                            // 1. 找到合适的设备来执行
                            AssetDevice assetDevice = taskProcessor.fetchDevice(task);
                            System.out.println("The device:"+assetDevice);
                            // 超时未找到设备，放弃尝试退出，任务执行失败
                            if (assetDevice == null) {
                                System.out.println("未发现可执行设备，任务失败");
                                return;
                            }

                            // 2. 执行中
                            taskProcessor.execute(task,  period);

                            // 3. 执行完成，更改task状态为完成
                            // 释放设备资源
                            taskProcessor.finish(task, assetDevice);
                            System.out.println("Finish Sequence Flow: " + sequenceFlow);
//                        }
                    } catch (Exception e) {
                        System.out.println("Error when executing Sequence Flow: "+sequenceFlow);
                        task.setStatus(-1);
                        taskDao.updateTask(task);
                    }
                });
                threadMap.put(targetRef,t);
                threadPool.submit(t);
                list.addLast(targetRef);
            }
            G[0].remove(sourceRef);
        }
        threadPool.shutdown();
    }
}
