package com.example.demo.controller.assembler;

//Needed for 'methodOn' and 'linkTo'
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.assembler.CustomOrderModelAssembler;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.CustomOrder;
import com.example.demo.model.Status;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class CustomOrderController extends ModelAssemblerCrudController<OrderService, CustomOrderModelAssembler, CustomOrder, Long> {

	@Autowired
	public CustomOrderController(OrderService customOrderService, CustomOrderModelAssembler assembler) {
		super(customOrderService, assembler);
	}

	@PostMapping("/employees/{id}")
	public ResponseEntity<EntityModel<CustomOrder>> newOrder(@RequestBody CustomOrder customOrder, @PathVariable Long id) {
		customOrder.setStatus(Status.IN_PROGRESS);
		customOrder.setEmployee(new Employee(id));
		EntityModel<CustomOrder> entityModel = assembler.toModel(service.save(customOrder));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@GetMapping("/employees/{id}")
	public CollectionModel<EntityModel<CustomOrder>> getAllOrderByEmployee(@PathVariable long id) {
		List<EntityModel<CustomOrder>> orders = service.findByEmployeeId(id).stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(orders, linkTo(methodOn(CustomOrderController.class).getAll()).withSelfRel());
	}

	@GetMapping("/employees/{empId}/{id}")
	public EntityModel<CustomOrder> getOrderById(@PathVariable Long id) {
		CustomOrder customOrder = service.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		return assembler.toModel(customOrder);
	}

	@PutMapping("/{id}/complete")
	public ResponseEntity<?> completeOrder(@PathVariable Long id) {
		CustomOrder order = service.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

		// You can "complete" an Order only if its precedent status was IN_PROGRESS
		// Check if the Order status is IN_PROGRESS, then return an OK response
		if (order.getStatus() == Status.IN_PROGRESS) {
			order.setStatus(Status.COMPLETED);
			return ResponseEntity.ok(assembler.toModel(service.update(order))); // 'ok()' is for status "HTTP 200 OK"
		}

		// If the Order current status isn't IN_PROGRESS, 'complete' isn't an allowed operation
		// The Order status isn't changed and a response 405 is sent to the client
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create().withTitle("Method not allowed")
						.withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
	}

	@DeleteMapping("/{id}/cancel")
	public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
		CustomOrder customOrder = service.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

		if (customOrder.getStatus() == Status.IN_PROGRESS) {
			customOrder.setStatus(Status.CANCELLED);
			return ResponseEntity.ok(assembler.toModel(service.update(customOrder)));
		}

		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create().withTitle("Method not allowed")
						.withDetail("You can't cancel an order that is in the " + customOrder.getStatus() + " status"));
	}
}