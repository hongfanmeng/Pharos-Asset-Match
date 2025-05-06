package com.zhd.service.impl;

import com.zhd.entity.AssetDevice;
import com.zhd.entity.Capability;
import com.zhd.entity.match.MatchItem;
import com.zhd.service.AssetService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AssetServiceImplTest {
    @Autowired
    private AssetService assetService;
    @Test
    void cap(){
        List<Capability> caps=new ArrayList<>();
        for(int i=1;i<=5;i++){
            caps.add(new Capability(
                    1L*i,
                    "cap"+i
            ));
        }
        assetService.insertCapability(caps);
    }
    @Test
    void setCap(){
        Map<Long,List<Integer>> map=new HashMap<>();
        List<Integer> list1=new ArrayList<>();
        list1.add(1);list1.add(2);
        map.put(1L,list1);
        List<Integer> list2=new ArrayList<>();
        list2.add(4);list2.add(5);
        map.put(2L,list2);
        List<Integer> list3=new ArrayList<>();
        list2.add(1);list2.add(3);
        map.put(3L,list3);
        assetService.setCapability(map);
    }

    @Test
    void registerAsset() {
        List<AssetDevice> list=new ArrayList<>();
        for(int i=1;i<=3;i++){
            list.add(new AssetDevice(
                    1L*i,
                    "devide"+i,
                    "company"+(i%2),
                    120.0+i*10,
                    0,
                    100.0+i*20,
                    100.0-i*5,
                    null
            ));
        }
        assetService.registerAsset(list);
    }

    @Test
    void getDevices() {
        List<AssetDevice> devices = assetService.getDevices();
        devices.forEach(System.out::println);
    }

    @Test
    void getLedger() {
        List<MatchItem> ledger = assetService.getLedger();
        ledger.forEach(System.out::println);
    }
}