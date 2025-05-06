package com.zhd.dao;

import com.zhd.entity.AssetDevice;
import com.zhd.entity.Task;
import com.zhd.entity.match.MatchItem;

import java.util.List;

public interface AssetMatchDao {
    MatchItem match(AssetDevice device, Task task);
    List<MatchItem> getMatchItems();
}
