package com.zhd.entity.flow;

import com.zhd.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SequenceFlow {
    private String id;
    private String sourceRef;
    private String targetRef;
    private Location location;
    private String time;
    private Integer period;
    private String conditionExpression;

}
