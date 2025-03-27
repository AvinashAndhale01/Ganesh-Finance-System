package com.ganesh;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LoanApprovalApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApprovalApplication.class, args);
	}

}
