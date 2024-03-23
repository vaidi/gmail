package com.atguigu.gmail.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/gateway")
public class HelloController {


    @GetMapping("/downgragde")
    public String hello(){
        return "<html>降级了</html>";
    }
}
