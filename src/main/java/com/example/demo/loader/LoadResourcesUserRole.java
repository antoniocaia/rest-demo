package com.example.demo.loader;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Configuration
public class LoadResourcesUserRole {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// Indicates that a method produces a bean to be managed by the Spring container
	// CommandLineRunner is an functional interface used to indicate that a bean should run.
	// 1- @Bean annotation means that a Bean will be generated
	// 2- The Bean will be of type 'CommandLineRunner', so a functional interface. 
	// 3- The Bean will be executed "automatically" after the application start 
	//@Bean // COMMENT BEAN TO AVOID POPULATE THE DB
	CommandLineRunner initDatabaseUser(UserRepository userRepository, RoleRepository roleRepository) { 
		return args -> {
			
			Role ra = new Role("ROLE_ADMIN", "Admin has permit over users", new HashSet<User>());
			Role ru = new Role("ROLE_USER", "User has permit over orders and employees", new HashSet<User>());
			
			roleRepository.save(ru);
			roleRepository.save(ra);
			
			User admin = new User("admin", passwordEncoder.encode("password"), new HashSet<Role>());
			User user = new User("user", passwordEncoder.encode("test"), new HashSet<Role>());
			
			admin.getRoles().add(ru);
			admin.getRoles().add(ra);
			user.getRoles().add(ru);
			
			userRepository.save(admin);
			userRepository.save(user);
		};
	}
}