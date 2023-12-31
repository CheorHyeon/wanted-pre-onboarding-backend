package com.cheor.wanted_10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Wanted10Application {

	public static void main(String[] args) {
		SpringApplication.run(Wanted10Application.class, args);
	}

}
