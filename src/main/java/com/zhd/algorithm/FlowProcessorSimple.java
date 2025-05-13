package com.zhd.algorithm;

import com.zhd.dao.TaskDao;
import com.zhd.entity.AssetDevice;
import com.zhd.entity.Task;
import com.zhd.entity.flow.Event;
import com.zhd.entity.flow.MetaTask;
import com.zhd.entity.flow.SequenceFlow;
import com.zhd.entity.flow.WorkFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Deprecated
@Component
public class FlowProcessorSimple {
    @Autowired
    private TaskProcessor taskProcessor;
    @Autowired
    private TaskDao taskDao;


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
        Event startEvent = workFlow.getStartEvent();
        LinkedList<String> list=new LinkedList<>();
        list.addLast(startEvent.getId());
        System.out.println("Start Event: "+startEvent.getName());

        Map<String, MetaTask> metaTaskMap=new HashMap<>();
        for(MetaTask metaTask: workFlow.getTasks()) metaTaskMap.put(metaTask.getId(),metaTask);
        while(!list.isEmpty()){
            String sourceRef = list.removeFirst();
            System.out.println(sourceRef);
            if(sourceRef.equals(workFlow.getEndEvent().getId())) break;
            List<SequenceFlow> sequenceFlows = G[0].get(sourceRef);
            for(SequenceFlow sequenceFlow:sequenceFlows){
                String targetRef=sequenceFlow.getTargetRef();
                    MetaTask metaTask = metaTaskMap.get(targetRef);
                    Task task = MetaTask.toTask(metaTask,sequenceFlow);
                    try {
                            System.out.println("Start Sequence Flow: " + sequenceFlow);
                            Integer period = sequenceFlow.getPeriod();
                            if (period == null||period<=0) period = 10;
                            // 1. 找到合适的设备来执行
                            AssetDevice assetDevice = taskProcessor.fetchDevice(task);
                            System.out.println("The device:"+assetDevice);
                            // 超时未找到设备，放弃尝试退出，任务执行失败
                            if (assetDevice == null) {
                                System.out.println("未发现可执行设备，任务失败");
                                return;
                            }

                            // 2. 执行中
                            taskProcessor.execute(assetDevice,task,  period);

                            // 3. 执行完成，更改task状态为完成
                            // 释放设备资源
                            taskProcessor.finish(task, assetDevice);
                            System.out.println("Finish Sequence Flow: " + sequenceFlow);
//                        }
                    } catch (Exception e) {
                        System.out.println("Error when executing Sequence Flow: "+e);
                        task.setStatus(-1);
                        taskDao.updateTask(task);
                    }

                list.addLast(targetRef);
            }
            G[0].remove(sourceRef);
        }

    }
}
