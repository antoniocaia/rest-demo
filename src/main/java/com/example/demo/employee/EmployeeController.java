package com.example.demo.employee;

// Needed for 'methodOn'
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RestController;

// @RestController is a short version for @Controller and @ResponseBody
// @Controller is a specialization of @Component (a class candidate for auto-detection when using annotation-based configuration and classpath scanning)
// Controller is used to tell Spring (?) that the annotated class contains method mapped to specific URI (using  @RequestMapping or @GetMapping, @PostMapping, etc)
// @ResponseBody indicates that the data returned by each method will be written straight into the response body instead of rendering a template
@RestController
class EmployeeController {

	  private final EmployeeService service;
	  private final EmployeeModelAssembler assembler;
	  
	  // "As of Spring Framework 4.3, an @Autowired annotation on such a constructor is no longer necessary if the target bean only defines one constructor to begin with"
	  // @Autowired
	  EmployeeController(EmployeeService service, EmployeeModelAssembler assembler) {
		  this.service = service;
		  this.assembler = assembler;
	  }
	
	// CollectionModel<EntityModel<T>> is a container of EntityModel<T> used by HATEOAS
	@GetMapping("/employees")
	CollectionModel<EntityModel<Employee>> all() {
		List<EntityModel<Employee>> employees = service.getAllEmployees().stream()
			.map(assembler::toModel)
			.collect(Collectors.toList());

		return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}

	@PostMapping("/employees")
	// ResponseEntity<T> is used to represent an HTTP response
	ResponseEntity<?> newEmployee(@RequestBody Employee newEmployee) {
		EntityModel<Employee> entityModel = assembler.toModel(service.saveEmployee(newEmployee));
		// '.created()' generate a "HTTP 201 Created" status. 'created' expect the location of the new instance as a parameter
		// To get the URI, we use the link stored in the EntityModel under the reference 'self'
		return ResponseEntity
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())		// code + location
			.body(entityModel);														// body of the HTTP response
	}
	
	@GetMapping("/employees/{id}")
	// EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links
	EntityModel<Employee> one(@PathVariable Long id) {
		Employee employee = service.getEmployee(id);
		return assembler.toModel(employee);
	}

	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
		return service.updateEmployee(newEmployee, id);
	}
	
	// @PathVariable bound the parameter 'Long id' to the URI template whit the SAME NAME
	// So if the URI is "/employees/{paperino}", we have to pass to the method the parameter '@PathVariable Long paperino'
	@DeleteMapping("/employees/{id}")
	ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
		service.deleteEmployee(id);
		return ResponseEntity.noContent().build();	// noContent correspond to a "HTTP 204 No Content" status
	}
}