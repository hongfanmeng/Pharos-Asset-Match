package com.zhd.controller;

import com.zhd.entity.AssetDevice;
import com.zhd.entity.Capability;
import com.zhd.entity.match.MatchItem;
import com.zhd.service.AssetService;
import com.zhd.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;
    /**
     * 批量注册设备
     * @param devices
     */
   @PutMapping("/devices")
    public void registerDevices(@RequestBody List<AssetDevice> devices){
       // 批量注册设备
       assetService.registerAsset(devices);
   }
   @PostMapping("/capability")
   public void insertCapability(@RequestBody List<Capability> capabilities){
       assetService.insertCapability(capabilities);
   }

   @PostMapping("/deviceCapability")
   public void setCapability(@RequestBody Map<Long,List<Integer>> map){
       assetService.setCapability(map);
   }

    /**
     * 查询设备状态
     * @return
     */
   @GetMapping("/devices")
   public List<AssetDevice> getDevices(){
       return assetService.getDevices();
   }

    /**
     * 查询电子账簿
     * @return
     */
   @GetMapping("/ledger")
    public List<MatchItem> getLedger(){
       return assetService.getLedger();
   }
}
