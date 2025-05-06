package com.zhd.entity.match;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhd.entity.AssetDevice;
import com.zhd.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="asset_match",autoResultMap = true)
public class MatchItem {
    Long taskId;
    Long deviceId;
    String provider;
    String consumer;
    Double leasingFee;
    Long startTime;  // timestamp
    Long leaseDuration;  // second
    Double cost;
//    @TableField(exist = false)
//    Task task;
//    @TableField(exist = false)
//    AssetDevice device;

}
