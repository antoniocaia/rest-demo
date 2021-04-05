package com.example.demo.loader;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Employee;
import com.example.demo.model.CustomOrder;
import com.example.demo.repository.EmployeeRepository;

@Configuration
public class LoadResourcesEmployee {

	private static final Logger log = LoggerFactory.getLogger(LoadResourcesEmployee.class);

	// Indicates that a method produces a bean to be managed by the Spring container

	// CommandLineRunner is an functional interface used to indicate that a bean should run.
	// 1- @Bean annotation means that a Bean will be generated
	// 2- The Bean will be of type 'CommandLineRunner', so a functional interface.
	// 3- The Bean will be executed "automatically" after the application start
	@Bean // COMMENT BEAN IF YOU DON'T WANT TO POPULATE THE DB
	@org.springframework.core.annotation.Order(2)
	CommandLineRunner initDatabaseEmployee(EmployeeRepository employeeRepository) {
		return args -> {
			log.info("Preloading " + employeeRepository.save(new Employee("Bilbo Baggins", "burglar", new HashSet<CustomOrder>())));
			log.info("Preloading " + employeeRepository.save(new Employee("Frodo Baggins", "thief", new HashSet<CustomOrder>())));
			log.info("Preloading " + employeeRepository.save(new Employee("Gandalf the Grey", "wizard", new HashSet<CustomOrder>())));
		};
	}
}