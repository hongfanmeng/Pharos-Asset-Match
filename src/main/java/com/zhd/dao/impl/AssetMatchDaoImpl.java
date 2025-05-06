package com.zhd.dao.impl;

import com.zhd.dao.AssetMatchDao;
import com.zhd.entity.AssetDevice;
import com.zhd.entity.Task;
import com.zhd.entity.match.MatchItem;
import com.zhd.mapper.AssetMatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AssetMatchDaoImpl implements AssetMatchDao {
    @Autowired
    private AssetMatchMapper assetMatchMapper;

    @Override
    public MatchItem match(AssetDevice assetDevice, Task task) {
        long current = new Date().getTime();
        long maxLeasingDuration = 10*60*60;
        MatchItem matchItem = new MatchItem(
                task.getTaskId(),
                assetDevice.getDeviceId(),
                assetDevice.getCompany(),
                task.getLeasingCompany(),
                assetDevice.getLeasingFee(),
                current,
                maxLeasingDuration,
                1.0 * maxLeasingDuration / 60 / 60 * assetDevice.getLeasingFee()
        );
        assetMatchMapper.insert(matchItem);
        return matchItem;
    }

    @Override
    public List<MatchItem> getMatchItems() {
        return assetMatchMapper.selectList(null);
    }
}
