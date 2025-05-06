package com.zhd.service.impl;

import com.zhd.dao.AssetMatchDao;
import com.zhd.dao.DeviceDao;
import com.zhd.entity.AssetDevice;
import com.zhd.entity.Capability;
import com.zhd.entity.match.MatchItem;
import com.zhd.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AssetServiceImpl implements AssetService {
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private AssetMatchDao assetMatchDao;

    @Override
    public void registerAsset(List<AssetDevice> devices) {
        deviceDao.registerDevice(devices);
    }

    @Override
    public List<AssetDevice> getDevices() {
        return deviceDao.getDevices();
    }

    @Override
    public List<MatchItem> getLedger() {
        return assetMatchDao.getMatchItems();
    }

    @Override
    public void setCapability(Map<Long, List<Integer>> map) {
        deviceDao.setCapability(map);
    }

    @Override
    public void insertCapability(List<Capability> capabilities) {
        deviceDao.insertCapability(capabilities);
    }
}
