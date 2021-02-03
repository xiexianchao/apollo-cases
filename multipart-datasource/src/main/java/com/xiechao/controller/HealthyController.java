package com.xiechao.controller;

import com.xiechao.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("healthy")
public class HealthyController {
    @Autowired
    private TestService testService;

    @RequestMapping("/healthy")
    public String healthy(){
         testService.testService();
         return "healthy";
    }
}
