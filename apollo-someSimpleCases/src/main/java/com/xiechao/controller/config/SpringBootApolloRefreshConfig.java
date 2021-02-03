package com.xiechao.controller.config;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringBootApolloRefreshConfig {


    private final RefreshScope refreshScope;
    private final RedisProperties redisProperties;

    public SpringBootApolloRefreshConfig(RefreshScope refreshScope, RedisProperties redisProperties) {
        this.refreshScope = refreshScope;
        this.redisProperties = redisProperties;
    }

    @ApolloConfigChangeListener(value = "someSimpleCases",interestedKeyPrefixes = "redis.cache")
    public void onChangeRedisConfig(ConfigChangeEvent changeEvent){
        log.info("before refresh {}", redisProperties.toString());
        refreshScope.refresh("redisProperties");
        log.info("after refresh {}", redisProperties.toString());

    }
}
