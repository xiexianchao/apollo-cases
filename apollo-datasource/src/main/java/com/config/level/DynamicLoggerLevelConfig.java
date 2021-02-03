package com.config.level;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;

@Configuration
@Slf4j
public class DynamicLoggerLevelConfig {
    private static final String LOGGER_TAG = "logging.level.";

    @Autowired
    private LoggingSystem loggingSystem;
    @ApolloConfig
    private Config config;


    public DynamicLoggerLevelConfig() {
        log.info("DynamicLoggerLevelConfig 构造函数执行");
    }

    @PostConstruct
    private void refreshLoggingLevels() {
        log.info("DynamiceLoggerLevelConfig postConstruct函数执行");
        log.info("从配置中心刷新日志配置");
        Set<String> propertyNames = config.getPropertyNames();
        for (String property : propertyNames) {
            if (StringUtils.containsIgnoreCase(property, LOGGER_TAG)) {
                String level = config.getProperty(property, "info");
                LogLevel logLevel = LogLevel.valueOf(level.toUpperCase());
                loggingSystem.setLogLevel(property.replace(LOGGER_TAG, ""), logLevel);
                log.info("{} : {}", property, level);
            }

        }
    }

    @ApolloConfigChangeListener(value = {"application"})
    private void onChange(ConfigChangeEvent changeEvent) {
        refreshLoggingLevels();
    }


}
