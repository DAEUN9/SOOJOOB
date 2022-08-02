package com.D210.soojoobback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SoojoobBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoojoobBackApplication.class, args);
	}

}
