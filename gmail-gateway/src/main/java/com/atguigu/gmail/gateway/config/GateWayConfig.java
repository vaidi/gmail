package com.atguigu.gmail.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {

//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder){
//        //获取路由
//        RouteLocatorBuilder.Builder routes = builder.routes();
//        /**
//         * 设置路由
//         * 1.路由id
//         * 2.路由匹配规则
//         * 3.目标地址
//         */
//        routes.route("path_route",r->r.path("/payment/*").uri("https://www.baidu.com")).build();
//        routes.route("path_route",r->r.path("/").uri("https://www.baidu.com")).build();
//        return routes.build();
//    }

}
