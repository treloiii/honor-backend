package com.honor.back.honorwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
public class HonorwebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(HonorwebappApplication.class, args);
	}

}
