package com.techmango.reports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.techmango.reports.repository")
public class ReportsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ReportsApplication.class, args);
	}
}