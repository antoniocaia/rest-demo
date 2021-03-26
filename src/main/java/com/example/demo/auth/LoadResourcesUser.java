package com.example.demo.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class LoadResourcesUser {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private static final Logger log = LoggerFactory.getLogger(LoadResourcesUser.class);

	// Indicates that a method produces a bean to be managed by the Spring container
	//@Bean // COMMENT BEAN IF YOU DON'T WANT TO POPULATE THE DB
	// CommandLineRunner is an functional interface used to indicate that a bean should run.
	// 1- @Bean annotation means that a Bean will be generated
	// 2- The Bean will be of type 'CommandLineRunner', so a functional interface. 
	// 3- The Bean will be executed "automatically" after the application start 
	CommandLineRunner initDatabaseUser(UserRepository userRepository) { 
		return args -> {
			log.info("Preloading " + userRepository.save(new User("admin", passwordEncoder.encode("password"), "ROLE_ADMIN")));
			log.info("Preloading " + userRepository.save(new User("user", passwordEncoder.encode("test"), "ROLE_USER")));
		};
	}
}