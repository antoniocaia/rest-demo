package com.example.demo.controller;

// Needed for 'methodOn'
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.assembler.EmployeeModelAssembler;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

// @RestController is a short version for @Controller and @ResponseBody
// @Controller is a specialization of @Component (a class candidate for auto-detection when using annotation-based configuration and classpath scanning)
// Controller is used to tell Spring (?) that the annotated class contains method mapped to specific URI (using  @RequestMapping or @GetMapping, @PostMapping, etc)
// @ResponseBody indicates that the data returned by each method will be written straight into the response body instead of rendering a template
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
	
	// I can remove the constructor and annotate this variable with @Autowired.
	// But during unit testing it's useful to have a constructor to manually inject mocked dependencies. Wtd?
	private final EmployeeService employeeService;
	private final EmployeeModelAssembler assembler;

	// "As of Spring Framework 4.3, an @Autowired annotation on such a constructor is no longer necessary 
	// if the target bean only defines one constructor to begin with"
	@Autowired
	public EmployeeController(EmployeeService service, EmployeeModelAssembler assembler) {
		this.employeeService = service;
		this.assembler = assembler;
	}

	// CollectionModel<EntityModel<T>> is a container of EntityModel<T> used by HATEOAS
	@GetMapping("/")
	public CollectionModel<EntityModel<Employee>> getAllEmployees() {
		List<EntityModel<Employee>> employees = employeeService.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel());
	}
	
	// ResponseEntity<T> is used to represent an HTTP response
	@PostMapping("/")
	public ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {
		EntityModel<Employee> entityModel = assembler.toModel(employeeService.save(newEmployee));
		// '.created()' generate a "HTTP 201 Created" status. 'created' expect the location of the new instance as a parameter
		// To get the URI, we use the link stored in the EntityModel under the reference 'self'
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) // code + location
				.body(entityModel); // body of the HTTP response
	}
	
	// EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links
	@GetMapping("/{id}")
	public EntityModel<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeService.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		return assembler.toModel(employee);
	}

	@PutMapping("/{id}")
	public Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		return employeeService.update(newEmployee, id);
	}

	// @PathVariable bound the parameter 'Long id' to the URI template whit the SAME NAME
	// So if the URI is "/employees/{paperino}", we have to pass to the method the parameter '@PathVariable Long paperino'
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
		employeeService.deleteById(id);
		return ResponseEntity.noContent().build(); // noContent correspond to a "HTTP 204 No Content" status
	}
}