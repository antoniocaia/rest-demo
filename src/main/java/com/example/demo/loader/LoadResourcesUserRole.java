package com.example.demo.loader;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Qualifier("standardEncoder")
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
			Role rw = new Role("ROLE_WATCHER", "Watcher... watchs", new HashSet<User>());
			Role r = new Role("READ", "READ", new HashSet<User>());
			Role w = new Role("WRITE", "WRITE", new HashSet<User>());
					
			roleRepository.save(ru);
			roleRepository.save(ra);
			roleRepository.save(rw);
			roleRepository.save(r);
			roleRepository.save(w);
			
			User admin = new User("admin", passwordEncoder.encode("password"), new HashSet<Role>(), false, false, true);
			User user = new User("user", passwordEncoder.encode("test"), new HashSet<Role>(),  false, false, true);
			User watcher = new User("watch", passwordEncoder.encode("pass"), new HashSet<Role>(),  false, false, true);
			
			admin.getRoles().add(ra);
			admin.getRoles().add(w);
			admin.getRoles().add(r);
			user.getRoles().add(ru);
			user.getRoles().add(r);
			user.getRoles().add(w);
			watcher.getRoles().add(rw);
			watcher.getRoles().add(r);

			userRepository.save(admin);
			userRepository.save(user);
			userRepository.save(watcher);
		};
	}
}