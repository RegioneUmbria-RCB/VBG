package com.paevolution.appiogateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AppiogatewayApplication {

    public static void main(String[] args) {

	SpringApplication.run(AppiogatewayApplication.class, args);
    }
}
