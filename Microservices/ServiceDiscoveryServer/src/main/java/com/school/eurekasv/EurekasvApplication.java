package com.school.eurekasv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekasvApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekasvApplication.class, args);
	}

}
