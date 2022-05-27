package com.paevolution.appioproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AppioproducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppioproducerApplication.class, args);
	}

}
