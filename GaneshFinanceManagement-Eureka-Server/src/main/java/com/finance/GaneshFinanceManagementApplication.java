package com.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class GaneshFinanceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(GaneshFinanceManagementApplication.class, args);
	}

}
