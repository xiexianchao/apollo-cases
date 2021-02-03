package com.controller;

import com.config.ds.DynamicDataSource;
import com.service.userService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/healthy")
@Slf4j
public class HealthyController {

    @Autowired
    private userService userService;

    @Autowired
    private DynamicDataSource dataSource;

    @Value("#{${mapCollections:{\"姓名\":{\"小明\",\"李华\"}}}}")
    private Map<String,List<String>> stringListMap;


    @RequestMapping(value = "/healthy")
    public String healthy(){
        String name = "original";
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select name from user where id = '1'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                name = resultSet.getString("name");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("name: " + name);
        return name;
    }

    @RequestMapping("/user")
    public String getUser(){
        return userService.queryUserName();
    }


    @RequestMapping("/easy")
    public Map getMap(){
        return stringListMap;
    }

}
