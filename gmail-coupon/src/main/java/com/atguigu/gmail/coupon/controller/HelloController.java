package com.atguigu.gmail.coupon.controller;

import com.atguigu.gmail.coupon.config.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
public class HelloController {

    @Autowired
    ServerInfo serverInfo;


    @GetMapping("/hello_coupon")
    public String hello(){
        return UUID.randomUUID().toString()+":"+serverInfo.getServerPort();
    }
}
