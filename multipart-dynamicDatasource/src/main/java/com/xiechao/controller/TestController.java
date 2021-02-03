package com.xiechao.controller;

import com.xiechao.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/healthy")
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/healthy")
    public String healthy(){
        testService.testService();
        return "healthy";
    }
}
