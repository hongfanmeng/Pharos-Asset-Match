package com.zhd.service;

import com.zhd.entity.AssetDevice;
import com.zhd.entity.Capability;
import com.zhd.entity.match.MatchItem;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface AssetService {
    void registerAsset(List<AssetDevice> devices);
    List<AssetDevice> getDevices();
    List<MatchItem> getLedger();
    void setCapability(Map<Long,List<Integer>> map);
    void insertCapability(List<Capability> capabilities);
}
