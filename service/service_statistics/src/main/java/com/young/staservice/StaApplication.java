package com.young.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.young"})
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.young.staservice.mapper")
@EnableSwagger2
@EnableScheduling
public class StaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}
