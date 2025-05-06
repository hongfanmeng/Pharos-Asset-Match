package com.zhd.entity.flow;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhd.entity.Task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="requirement",autoResultMap = true)
public class Requirement {
    private String id;
    private String requirement;
    private String description;
    private Double longitude;
    private Double latitude;
    private String company;
    @TableField(exist = false)
    private List<Task> tasks;
}
