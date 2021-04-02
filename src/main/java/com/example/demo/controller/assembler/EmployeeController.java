package com.example.demo.controller.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.assembler.EmployeeModelAssembler;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

// @RestController is a short version for @Controller and @ResponseBody
// @Controller is a specialization of @Component (a class candidate for auto-detection when using annotation-based configuration and classpath scanning)
// Controller is used to tell Spring (?) that the annotated class contains method mapped to specific URI (using  @RequestMapping or @GetMapping, @PostMapping, etc)
// @ResponseBody indicates that the data returned by each method will be written straight into the response body instead of rendering a template
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController extends ModelAssemblerCrudController<EmployeeService, EmployeeModelAssembler, Employee, Long>{
		
	@Autowired
	public EmployeeController(EmployeeService service, EmployeeModelAssembler assembler) {
		super(service, assembler);
	}
	
	// I can remove the constructor and annotate this variable with @Autowired.
	// But during unit testing it's useful to have a constructor to manually inject mocked dependencies. Wtd?
	// private final EmployeeService employeeService;
	// private final EmployeeModelAssembler assembler;

	// "As of Spring Framework 4.3, an @Autowired annotation on such a constructor is no longer necessary 
	// if the target bean only defines one constructor to begin with"
	// // @Autowired
	// public EmployeeController(EmployeeService service, EmployeeModelAssembler assembler) {
	// 	 this.employeeService = service;
	//	 this.assembler = assembler;
	// }
}