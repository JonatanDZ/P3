package com.example.p3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class P3Application {

    public static void main(String[] args) {

        SpringApplication.run(P3Application.class, args);

    }

}
