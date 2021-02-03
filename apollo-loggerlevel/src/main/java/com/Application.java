package com;


import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableApolloConfig(value = {"application"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        System.out.println("===============任务启动================");
    }
}
