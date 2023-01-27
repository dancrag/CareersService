package com.by.CareersService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CareersServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareersServiceApplication.class, args);
	}

}
