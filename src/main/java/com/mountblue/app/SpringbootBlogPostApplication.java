package com.mountblue.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringbootBlogPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogPostApplication.class, args);
	}

}
