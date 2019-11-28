package com.item.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@RibbonClient(name = "product-microservice")
@EnableFeignClients
@SpringBootApplication
public class ItemMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemMicroserviceApplication.class, args);
    }

}
