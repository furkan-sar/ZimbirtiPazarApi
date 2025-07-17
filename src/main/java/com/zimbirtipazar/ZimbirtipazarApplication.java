package com.zimbirtipazar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zimbirtipazar"})
@EntityScan(basePackages = {"com.zimbirtipazar"})
@EnableJpaRepositories(basePackages = {"com.zimbirtipazar"})
public class ZimbirtipazarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZimbirtipazarApplication.class, args);
	}

}
