package com.zhd.entity.flow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkFlow {
    private Event startEvent;
    private Event endEvent;
    private MetaTask[] tasks;
    private SequenceFlow[] sequenceFlows;
}
