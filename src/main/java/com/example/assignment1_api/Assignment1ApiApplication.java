package com.example.assignment1_api;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableProcessApplication
@EnableFeignClients
@SpringBootApplication
@EnableJpaAuditing
public class Assignment1ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(Assignment1ApiApplication.class, args);
    }
}
