package com.example.lease_by;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LeaseByApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaseByApplication.class, args);
	}

}
