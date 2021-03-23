package com.example.demo.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadResourcesUser {

	private static final Logger log = LoggerFactory.getLogger(LoadResourcesUser.class);

	// Indicates that a method produces a bean to be managed by the Spring container
	@Bean
	// CommandLineRunner is an functional interface used to indicate that a bean should run.
	// 1- @Bean annotation means that a Bean will be generated
	// 2- The Bean will be of type 'CommandLineRunner', so a functional interface. 
	// 3- The Bean will be executed "automatically" after the application start 
	CommandLineRunner initDatabaseUser(UserRepository employeeRepository) { 
		return args -> {
			log.info("Preloading " + employeeRepository.save(new User("test", "password")));
		};
	}
}