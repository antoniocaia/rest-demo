package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.CustomOrderNotFoundException;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.CustomOrder;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

//@RestController is a short version for @Controller and @ResponseBody
//@Controller is a specialization of @Component (a class candidate for auto-detection when using annotation-based configuration and classpath scanning)
//Controller is used to tell Spring (?) that the annotated class contains method mapped to specific URI (using  @RequestMapping or @GetMapping, @PostMapping, etc)
//@ResponseBody indicates that the data returned by each method will be written straight into the response body instead of rendering a template
@RestController
@RequestMapping(value = "/api/v1/employees", produces = "application/json") // TODO BUG: some mapping always require '/' at the end
public class EmployeeController extends BaseCrudController<EmployeeService, Employee, Long> {
	// I can remove the constructor and annotate this variable with @Autowired.
	// But during unit testing it's useful to have a constructor to manually inject mocked dependencies. Wtd?
	// private final EmployeeService employeeService;
	// private final EmployeeModelAssembler assembler;

	// "As of Spring Framework 4.3, an @Autowired annotation on such a constructor is no longer necessary
	// if the target bean only defines one constructor to begin with"
	// // @Autowired
	public EmployeeController(EmployeeService service) {
		super(service);
	}
	
	@GetMapping("/{id}/order")
	public List<CustomOrder> getCustomOrders(@PathVariable Long id) {
		Employee employee = service.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		return employee.getOrders().stream().collect(Collectors.toList());
	}
	
	@GetMapping("/{id}/order/{idO}")
	public CustomOrder getCustomOrderById(@PathVariable Long id, @PathVariable Long idO) {
		Employee employee = service.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		return employee.getOrders().stream().filter(o -> o.getId() == idO).findFirst().orElseThrow(() -> new CustomOrderNotFoundException(idO));
	}
}
