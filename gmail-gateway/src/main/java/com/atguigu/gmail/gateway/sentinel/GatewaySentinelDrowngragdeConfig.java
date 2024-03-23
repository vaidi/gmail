package com.atguigu.gmail.gateway.sentinel;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.apache.http.HttpStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 降级处理
 */
@Configuration
public class GatewaySentinelDrowngragdeConfig implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                return ServerResponse.status(HttpStatus.SC_OK).contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue("降级了！！！"));
            }
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }
}
