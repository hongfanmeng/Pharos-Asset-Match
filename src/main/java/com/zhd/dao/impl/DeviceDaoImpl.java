package com.zhd.dao.impl;

import com.zhd.dao.DeviceDao;
import com.zhd.entity.AssetDevice;
import com.zhd.entity.Capability;
import com.zhd.entity.Task;
import com.zhd.mapper.AssetDeviceMapper;
import com.zhd.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DeviceDaoImpl implements DeviceDao {
    @Autowired
    private AssetDeviceMapper assetDeviceMapper;
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public AssetDevice selectOneValidDevice(Task task){
        List<AssetDevice> assetDevices = assetDeviceMapper.selectValidDevices(task.getCapId());
        return assetDevices==null||assetDevices.isEmpty()?null:assetDevices.get(0);
    }

    @Override
    public void updateDevice(AssetDevice assetDevice) {
        assetDeviceMapper.updateById(assetDevice);
    }

    @Override
    public void registerDevice(List<AssetDevice> assetDevices) {
        for(AssetDevice assetDevice:assetDevices){
            assetDeviceMapper.insert(assetDevice);
        }
    }

    @Override
    public List<AssetDevice> getDevices() {
        List<AssetDevice> assetDevices = assetDeviceMapper.selectList(null);
        for(AssetDevice assetDevice:assetDevices){
            assetDevice.setCapabilityList(assetDeviceMapper.getCapabilityByDevice(assetDevice.getDeviceId()));
        }
        return assetDevices;
    }

    @Override
    public void setCapability(Map<Long, List<Integer>> map) {
        for(Long deviceId:map.keySet()){
            List<Integer> caps=map.get(deviceId);
            for(Integer cap:caps){
                assetDeviceMapper.insertDeviceCapability(deviceId,1L*cap);
            }
        }
    }

    @Override
    public void insertCapability(List<Capability> capabilities) {
        for(Capability capability:capabilities){
            assetDeviceMapper.insertCapability(capability);
        }
    }

    @Override
    public String getTrack(Long deviceId) {
        return assetDeviceMapper.getTrack(deviceId);
    }
}
