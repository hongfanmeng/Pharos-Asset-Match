package com.zhd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.AsyncRestTemplate;


@EntityScan("com.zhd.entity")
@MapperScan("com.zhd.mapper")
@SpringBootApplication
@EnableAsync
public class MatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchApplication.class, args);
    }

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }

    // TODO
    /**
     * 任务的 DAG图 只需从头到尾只需找到一条路径，根据机器人的位置的决定
     * 增加上述的判断逻辑,做匹配
     * 第一阶段：单个子任务只有一个设备来做
     * 第二阶段：单个任务做接力导航
     */

    /**
     * 葡萄牙出租车司机的数据
     * 增加 任务执行时根据轨迹移动无人车的功能： 每隔1s变化到轨迹下一个位置
     *  TODO 任务执行过程有问题，是并发的而不是串行
     */

}