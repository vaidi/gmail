package com.atguigu.gmail.gateway.dynamicroute;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executor;

import static com.atguigu.gmail.gateway.dynamicroute.NacosGatewayConfig.*;
@Slf4j
//@Service
//@DependsOn({"nacosGatewayConfig"})
public class NacosDynamicRouteService {

    @Autowired
    private NacosRefreshRouteService nacosRefreshRouteService;


    private ConfigService configService;

    @Autowired
    private ObjectMapper objectMapper;
/*    @PostConstruct
    public void init() {
        log.info("开始网关动态路由初始化...");
        try {
            // getConfigAndSignListener()方法 发起长轮询和对dataId数据变更注册监听的操作
            // getConfig 只是发送普通的HTTP请求
            String initConfigInfo = configService.getConfigAndSignListener(configProperties.getDataId(), configProperties.getGroup(), nacosConfigProperties.getTimeout(), new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    if (StringUtils.isNotEmpty(configInfo)) {
                        log.info("接收到网关路由更新配置：\r\n{}", configInfo);
                        List<RouteDefinition> routeDefinitions = null;
                        try {
                            routeDefinitions = objectMapper.readValue(configInfo, new TypeReference<List<RouteDefinition>>() {
                            });
                        } catch (JsonProcessingException e) {
                            log.error("解析路由配置出错，" + e.getMessage(), e);
                        }
                        for (RouteDefinition definition : Objects.requireNonNull(routeDefinitions)) {
                            routeService.update(definition);
                        }
                    } else {
                        log.warn("当前网关无动态路由相关配置");
                    }
                }
            });
            log.info("获取网关当前动态路由配置:\r\n{}", initConfigInfo);
            if (StringUtils.isNotEmpty(initConfigInfo)) {
                List<RouteDefinition> routeDefinitions = objectMapper.readValue(initConfigInfo, new TypeReference<List<RouteDefinition>>() {
                });
                for (RouteDefinition definition : routeDefinitions) {
                    routeService.add(definition);
                }
            } else {
                log.warn("当前网关无动态路由相关配置");
            }
            log.info("结束网关动态路由初始化...");
        } catch (Exception e) {
            log.error("初始化网关路由时发生错误", e);
        }
    }*/


    @PostConstruct
    public void init() {
        log.info(">>>>>>>>>> init gateway route <<<<<<<<<<");
        configService = initConfigService();
        if (null == configService) {
            log.error(">>>>>>> init the ConfigService failed!!!");
        }
        String configInfo = null;
        try {
            configInfo = configService.getConfig(NACOS_ROUTE_DATA_ID, NACOS_ROUTE_GROUP, DEFAULT_TIMEOUT);
            log.info(">>>>>>>>> get the gateway configInfo: {}", configInfo);
            List<CustomizedRouteDefinition> routeDefinitions = objectMapper.readValue(configInfo, new TypeReference<List<CustomizedRouteDefinition>>() {});
            for (RouteDefinition definition : routeDefinitions) {
                log.info(">>>>>>>>>> load route : {}", definition.toString());
                nacosRefreshRouteService.add(definition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        dynamicRouteByNacosListener(NACOS_ROUTE_DATA_ID, NACOS_ROUTE_GROUP);
    }


    private void dynamicRouteByNacosListener(String dataId, String group) {
        try {
            configService.addListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    log.info("-------------------getExecutor-------------------");
                    return null;
                }
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info(">>>>>>>>> listened configInfo change: {}", configInfo);
                    List<CustomizedRouteDefinition> routeDefinitions = null;
                    try {
                        routeDefinitions = objectMapper.readValue(configInfo, new TypeReference<List<CustomizedRouteDefinition>>() {});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    nacosRefreshRouteService.updateList(routeDefinitions);
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }


    private ConfigService initConfigService() {
        Properties properties = new Properties();
        properties.setProperty("serverAddr", NACOS_SERVER_ADDR);
        properties.setProperty("namespace", NACOS_NAMESPACE);
        try {
            return NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }
    }
}
