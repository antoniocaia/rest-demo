package com.example.demo.loader;

import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.demo.model.CustomOrder;
import com.example.demo.model.Employee;
import com.example.demo.model.Status;
import com.example.demo.repository.CustomOrderRepository;
import com.example.demo.repository.EmployeeRepository;

@Configuration
@Profile("test")
public class LoadResourcesEmployeeOrder {

	// CommandLineRunner is an functional interface used to indicate that a bean should run.
	// 1- @Bean annotation means that a Bean will be generated
	// 2- The Bean will be of type 'CommandLineRunner', so a functional interface.
	// 3- The Bean will be executed "automatically" after the application start
	@Bean // COMMENT BEAN IF YOU DON'T WANT TO POPULATE THE DB
	CommandLineRunner initDatabaseEmployee(EmployeeRepository employeeRepository, CustomOrderRepository orderRepository) {
		return args -> {

			Employee bb = new Employee("Bilbo Baggins", "burglar", new HashSet<CustomOrder>());
			Employee fb = new Employee("Frodo Baggins", "thief", new HashSet<CustomOrder>());
			Employee gg = new Employee("Gandalf the Grey", "wizard", new HashSet<CustomOrder>());
			
			employeeRepository.save(bb);
			employeeRepository.save(fb);
			employeeRepository.save(gg);
			
			CustomOrder ring = new CustomOrder("The Ring", Status.IN_PROGRESS, fb);
			CustomOrder staff = new CustomOrder("Magic Staff", Status.COMPLETED, gg);
			CustomOrder stuff = new CustomOrder("Stuff", Status.COMPLETED, bb);
			CustomOrder hat = new CustomOrder("Hat", Status.COMPLETED, gg);
			
			orderRepository.save(ring);
			orderRepository.save(staff);
			orderRepository.save(stuff);
			orderRepository.save(hat);
			
			bb.getOrders().add(stuff);
			fb.getOrders().add(ring);
			gg.getOrders().add(hat);
			gg.getOrders().add(staff);
			
		};
	}
}