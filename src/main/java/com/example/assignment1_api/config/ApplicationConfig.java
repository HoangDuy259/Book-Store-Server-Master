package com.example.assignment1_api.config;

import com.example.assignment1_api.utils.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public StringUtils util(){return new StringUtils();}
}
