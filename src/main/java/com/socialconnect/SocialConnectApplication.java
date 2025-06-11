package com.socialconnect;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan("com.socialconnect.repository")
public class SocialConnectApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocialConnectApplication.class, args);
    }
} 