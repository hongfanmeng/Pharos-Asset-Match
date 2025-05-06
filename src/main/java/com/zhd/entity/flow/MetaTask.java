package com.zhd.entity.flow;

import com.zhd.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaTask {
//    String id;
//    Integer type;
//    Object[] payloadList;

    String id;
    String leasingCompany;
    String requirementId;
    Long capId;

    public static Task toTask(MetaTask metaTask,SequenceFlow sequenceFlow){
        Task task = new Task();
        if(metaTask!=null){
            task.setTaskName(metaTask.getId());
            task.setLeasingCompany(metaTask.getLeasingCompany());
            task.setRequirementId(metaTask.getRequirementId());
            task.setCapId(metaTask.getCapId());
        }

        if(sequenceFlow!=null){
            if(sequenceFlow.getLocation()!=null){
                task.setLongitude(sequenceFlow.getLocation().getLongitude());
                task.setLatitude(sequenceFlow.getLocation().getLatitude());
            }
            task.setTime(sequenceFlow.getTime());
            task.setPeriod(sequenceFlow.getPeriod());
            task.setConditionExpression(sequenceFlow.getConditionExpression());
        }
        task.setStatus(0);
        return task;
    }
}
