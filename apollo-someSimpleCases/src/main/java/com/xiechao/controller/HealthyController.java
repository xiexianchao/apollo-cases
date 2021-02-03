package com.xiechao.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/healthy")
@Slf4j
public class HealthyController {

    @Value("${key1}")
    private String key1;

    @ApolloConfig
    private Config config;

    @ApolloJsonValue("${jsonBeans:[]}")
    private List<User> userList;

    @ApolloJsonValue("${user1}")
    private User user1;

    @RequestMapping(value = "/easy")
    public String healthy(){
        log.info("key1 : " + key1);
        String key2 = config.getProperty("key2","default");
        log.info("key2 : " + key2);
        log.info("userList: " + Arrays.toString(userList.toArray()));
        log.info("user1: " + user1);
        return "healthy";
    }

}
@Data
class User{
    String id;
    String name;
}