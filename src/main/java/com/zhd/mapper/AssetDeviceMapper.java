package com.zhd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhd.entity.AssetDevice;
import com.zhd.entity.Capability;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AssetDeviceMapper extends BaseMapper<AssetDevice> {
    @Select("SELECT * FROM asset_device d WHERE status=0 and d.device_id in (" +
            " SELECT device_id FROM device_capability dc WHERE dc.cap_id=#{capId}" +
            ")")
    List<AssetDevice> selectValidDevices(Long capId);

    @Update("INSERT INTO device_capability VALUES (#{deviceId},#{capId})")
    void insertDeviceCapability(Long deviceId,Long capId);

    @Update("INSERT INTO capability VALUES (#{capId},#{capName})")
    void insertCapability(Capability capability);

    @Select("SELECT * FROM capability WHERE cap_id in (" +
            "  SELECT cap_id FROM device_capability WHERE device_id=#{deviceId}" +
            ")")
    List<Capability> getCapabilityByDevice(Long deviceId);

    @Select("SELECT track FROM taxi_track WHERE device_id=#{deviceId}")
    String getTrack(Long deviceId);
}
