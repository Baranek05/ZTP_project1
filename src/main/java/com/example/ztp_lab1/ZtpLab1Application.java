package com.example.ztp_lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ZtpLab1Application {

    public static void main(String[] args) {
        SpringApplication.run(ZtpLab1Application.class, args);
    }

}
