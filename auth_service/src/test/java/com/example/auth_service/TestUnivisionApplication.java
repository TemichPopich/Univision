package com.example.auth_service;

import org.springframework.boot.SpringApplication;

public class TestUnivisionApplication {

	public static void main(String[] args) {
		SpringApplication.from(AuthService::main).with(TestcontainersConfiguration.class).run(args);
	}

}
