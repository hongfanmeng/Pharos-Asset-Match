package com.zhd.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantValue {
    @Value("${flow.url}")
    public  String flowUrl;
}
