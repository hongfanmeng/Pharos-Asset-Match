package com.zhd.service.impl;

import com.zhd.constant.ConstantValue;
import com.zhd.dao.AssetMatchDao;
import com.zhd.dao.DeviceDao;
import com.zhd.entity.AssetDevice;
import com.zhd.entity.Capability;
import com.zhd.entity.match.MatchItem;
import com.zhd.service.AssetService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AssetServiceImpl implements AssetService {
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private AssetMatchDao assetMatchDao;
    @Autowired
    private ConstantValue constantValue;
    @Autowired
    private DataSource dataSource;

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

    @Override
    public void resetDB() {
        try (Connection conn = dataSource.getConnection()) {
            ScriptRunner runner = new ScriptRunner(conn);
            runner.runScript(new FileReader(constantValue.dataGenScript));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
