package com.zhd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhd.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskMapper extends BaseMapper<Task> {

    @Select("SELECT * FROM task WHERE requirement_id = #{requirementId}")
    List<Task> findByRequirementId(String requirementId);
}
