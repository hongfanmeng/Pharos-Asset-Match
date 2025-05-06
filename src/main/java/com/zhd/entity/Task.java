package com.zhd.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="task",autoResultMap = true)
public class Task {
    @TableId(type= IdType.AUTO)
    Long taskId;
    String taskName;
    String leasingCompany;
    String requirementId;
    Long capId;
    Double longitude;
    Double latitude;
    String time;  // 机器人需要在time这个时间戳之前到达(long,lati)位置，然后开始执行period的时长
    Integer period; // 单位 秒
    String conditionExpression; // 提示词
    Integer status; // 状态 0-新创建  1-未开始  2-进行中  3-已完成  4-未抢到资源而退出  -1 -异常结束
}
