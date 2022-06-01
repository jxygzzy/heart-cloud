package com.heartcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HeartCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeartCloudApplication.class, args);
    }

}
