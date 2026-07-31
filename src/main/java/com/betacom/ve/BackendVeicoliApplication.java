package com.betacom.ve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendVeicoliApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendVeicoliApplication.class, args);
	}

}
