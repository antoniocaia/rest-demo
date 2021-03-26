package com.example.demo.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.demo.employee.Employee;

@Configuration
public class LoadResourcesOrders {

	private static final Logger log = LoggerFactory.getLogger(LoadResourcesOrders.class);

	// Indicates that a method produces a bean to be managed by the Spring container
	//@Bean // COMMENT BEAN IF YOU DON'T WANT TO POPULATE THE DB
	// CommandLineRunner is an functional interface used to indicate that a bean should run.
	// 1- @Bean annotation means that a Bean will be generated
	// 2- The Bean will be of type 'CommandLineRunner', so a functional interface. 
	// 3- The Bean will be executed "automatically" after the application start 
	CommandLineRunner initDatabaseOrder(OrderRepository orderRepository) { 
		return args -> {
			log.info("Preloading " + orderRepository.save(
					new Order("MacBook", Status.IN_PROGRESS, new Employee(1, "", ""))));
			log.info("Preloading " + orderRepository.save(
					new Order("Tech", Status.COMPLETED, new Employee(1, "", ""))));
		};
	}
}