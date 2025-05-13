package com.zhd.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantValue {
    @Value("${flow.url}")
    public  String flowUrl;
    @Value("${spring.datasource.url}")
    public String dbUrl;
    @Value("${spring.datasource.username}")
    public String dbUsername;
    @Value("${spring.datasource.password}")
    public String dbPwd;
    @Value("${script.data-gen}")
    public String dataGenScript;
}
