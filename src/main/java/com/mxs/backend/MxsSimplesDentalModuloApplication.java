package com.mxs.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MxsSimplesDentalModuloApplication {
	public static void main(String[] args) {
		SpringApplication.run(MxsSimplesDentalModuloApplication.class, args);
	}
}
