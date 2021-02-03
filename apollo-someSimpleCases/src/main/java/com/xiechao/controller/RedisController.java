package com.xiechao.controller;

import com.xiechao.controller.config.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/redis")
@Slf4j
public class RedisController {

    @Autowired
    private RedisProperties redisProperties;

    @RequestMapping(value = "/easy")
    public String healthy(){
        log.info("redisProperties: timeout = {}, expire = {}" , redisProperties.getCommandTimeout(), redisProperties.getExpireSeconds());
        return "healthy";
    }
}
