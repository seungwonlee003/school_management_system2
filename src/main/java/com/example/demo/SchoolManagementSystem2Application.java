package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.example.demo")
public class SchoolManagementSystem2Application {

	public static void main(String[] args) {
		SpringApplication.run(SchoolManagementSystem2Application.class, args);
	}
}
