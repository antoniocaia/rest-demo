package com.example.demo.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.demo.model.Employee;
import com.example.demo.controller.*;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {

	@Override
	// EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links
	// For each link we specify a method and a relation. When the link is build, the URI mapped to the method is used.
	public EntityModel<Employee> toModel(Employee employee) {
		// Build an EntityModel that will contain all the employee attribute plus a '_link' field containing all the specified links
		return EntityModel.of(
				employee, 
				linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel(),	// Links the method one(), and define its type as self link, or self reference
				linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));			// Links the method all(), and call it "employees"
	}	
}