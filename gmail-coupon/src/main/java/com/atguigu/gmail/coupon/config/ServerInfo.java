package com.atguigu.gmail.coupon.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ServerInfo {

    @Value("${server.port}")
    private String serverPort;
}
