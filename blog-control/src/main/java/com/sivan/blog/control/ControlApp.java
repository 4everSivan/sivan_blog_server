package com.sivan.blog.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sivan
 * @version 1.0
 * @date 2022/3/8 15:28
 */

@SpringBootApplication
public class ControlApp {

    public static void main(String[] args) {
        SpringApplication.run(ControlApp.class,args);
    }

//    @Bean
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }
}
