package com.atguigu.gmail.gateway.ratelimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 令牌桶算法
 */
@Component
@Slf4j
public class UriKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        String host = exchange.getRequest().getURI().getHost();
        log.info("限流host:{}",host);
        return Mono.just(host);
    }
}
