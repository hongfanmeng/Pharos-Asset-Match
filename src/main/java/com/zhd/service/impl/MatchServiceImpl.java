package com.zhd.service.impl;

import com.zhd.algorithm.FlowProcessor;
import com.zhd.algorithm.FlowProcessorSimple;
import com.zhd.dao.AssetMatchDao;
import com.zhd.dao.DeviceDao;
import com.zhd.dao.TaskDao;
import com.zhd.entity.AssetDevice;
import com.zhd.entity.Task;
import com.zhd.entity.flow.SequenceFlow;
import com.zhd.entity.flow.WorkFlow;
import com.zhd.entity.match.MatchItem;
import com.zhd.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private FlowProcessor flowProcessor;



    @Override
    public void executeFlow(WorkFlow workFlow) {
        Map<String, List<SequenceFlow>> []graph = flowProcessor.generateGraph(workFlow);
        flowProcessor.parallelExecute(workFlow,graph,500);
    }


}
