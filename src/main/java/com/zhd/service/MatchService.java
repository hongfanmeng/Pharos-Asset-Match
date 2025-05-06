package com.zhd.service;


import com.zhd.entity.Task;
import com.zhd.entity.match.MatchItem;
import com.zhd.entity.flow.SequenceFlow;
import com.zhd.entity.flow.WorkFlow;

import java.util.List;
import java.util.Map;

public interface MatchService {
    void executeFlow(WorkFlow workFlow);
}
