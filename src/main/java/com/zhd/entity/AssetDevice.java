package com.zhd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="asset_device",autoResultMap = true)
public class AssetDevice {
    @TableId(type = IdType.AUTO)
    Long deviceId;
    String deviceName;
    String company;
    Double leasingFee; // per hour
    Integer status; // 0-空闲  1-繁忙
    Double longitude;
    Double latitude;
    @TableField(exist = false)
    List<Capability> capabilityList;
}
