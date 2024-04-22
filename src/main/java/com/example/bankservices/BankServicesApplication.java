package com.example.bankservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.bankservices.repository")
@EntityScan( "com.example.bankservices.model")

public class BankServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankServicesApplication.class, args);
	}

}
