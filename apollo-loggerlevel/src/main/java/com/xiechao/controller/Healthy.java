package com.xiechao.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/healthy")
@Slf4j
public class Healthy {

    @RequestMapping(value = "healthy")
    public String hello(){
        log.info("info: ");
        log.error("error: ");
        log.warn("warn: ");
        log.debug("debug: ");
        return "healthy";
    }
}
