package com.zhd.dao;

import com.zhd.entity.AssetDevice;
import com.zhd.entity.Capability;
import com.zhd.entity.Task;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface DeviceDao {
     AssetDevice selectOneValidDevice(Task task);
     void updateDevice(AssetDevice assetDevice);
     void registerDevice(List<AssetDevice> assetDevices);
     List<AssetDevice> getDevices();
     void setCapability(Map<Long,List<Integer>> map);
     void insertCapability(List<Capability> capabilities);
}
