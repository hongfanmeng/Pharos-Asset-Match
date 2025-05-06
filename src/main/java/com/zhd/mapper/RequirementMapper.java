package com.zhd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhd.entity.flow.Requirement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Many;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RequirementMapper extends BaseMapper<Requirement> {

    @Select("SELECT * FROM requirement")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "requirement", column = "requirement"),
            @Result(property = "description", column = "description"),
            @Result(property = "longitude", column = "longitude"),
            @Result(property = "latitude", column = "latitude"),
            @Result(property = "company", column = "company"),
            @Result(property = "tasks", column = "id", many = @Many(select = "com.zhd.mapper.TaskMapper.findByRequirementId"))
    })
    List<Requirement> selectAllWithTasks();
}
