package com.xiechao;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@Slf4j
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableApolloConfig(value = "multipart-dynamicDataSource")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        System.out.println("========任务启动===========");
    }
}
