package com.mountblue.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@EnableJpaRepositories
public class SpringbootBlogPostApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogPostApplication.class, args);
	}
}
