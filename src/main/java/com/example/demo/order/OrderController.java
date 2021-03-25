package com.example.demo.order;

//Needed for 'methodOn' and 'linkTo'
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.employee.Employee;

@RestController
class OrderController {

	private final OrderRepository orderRepository;
	private final OrderService orderService;
	private final OrderModelAssembler assembler;

	OrderController(OrderRepository orderRepository, OrderModelAssembler assembler, OrderService orderService ) {
		this.orderRepository = orderRepository;
		this.orderService = orderService;
		this.assembler = assembler;
	}

	@GetMapping("/orders")
	CollectionModel<EntityModel<Order>> all() {
		List<EntityModel<Order>> orders = orderService.getAllOrders().stream()
			.map(assembler::toModel)
			.collect(Collectors.toList());

		return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).all()).withSelfRel());
	}
	
	@PostMapping("employees/{id}/orders")
	ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order, @PathVariable Long id) {
		order.setStatus(Status.IN_PROGRESS);
		order.setEmployee(new Employee(id));
		EntityModel<Order> entityModel = assembler.toModel(orderService.saveOrder(order));
		
		return ResponseEntity
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
			.body(entityModel);
	}
	
	@GetMapping("/employees/{id}/orders")
	CollectionModel<EntityModel<Order>> getAllOrderByEmployee(@PathVariable long id){
		List<EntityModel<Order>>  orders = orderService.getOrdersByEmployee(id).stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).all()).withSelfRel());
	}
	
	@GetMapping("/employees/{empId}/orders/{id}")
	EntityModel<Order> one(@PathVariable Long id) {
		Order order = orderService.getOrder(id);
		return assembler.toModel(order);
	}
	
	@PutMapping("/orders/{id}/complete")
	ResponseEntity<?> complete(@PathVariable Long id) {
		Order order = orderService.getOrder(id);
		
		// You can "complete" an Order only if its precedent status was IN_PROGRESS
		// Check if the Order status is IN_PROGRESS, then return an OK response
		if (order.getStatus() == Status.IN_PROGRESS) {
			order.setStatus(Status.COMPLETED);
			return ResponseEntity.ok(assembler.toModel(orderRepository.save(order))); // 'ok()' is for status "HTTP 200 OK"
		}
		
		// If the Order current status isn't IN_PROGRESS, 'complete' isn't an allowed operation
		// The Order status isn't changed and a response 405 is sent to the client
		return ResponseEntity 
			.status(HttpStatus.METHOD_NOT_ALLOWED) 
			.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
			.body(Problem.create() 
				.withTitle("Method not allowed") 
				.withDetail("You can't complete an order that is in the " + order.getStatus() + " status"));
	}
	
	@DeleteMapping("/orders/{id}/cancel")
	ResponseEntity<?> cancel(@PathVariable Long id) {
		Order order = orderService.getOrder(id);

		if (order.getStatus() == Status.IN_PROGRESS) {
			order.setStatus(Status.CANCELLED);
			return ResponseEntity.ok(assembler.toModel(orderRepository.save(order)));
		}

		return ResponseEntity 
			.status(HttpStatus.METHOD_NOT_ALLOWED) 
			.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) 
			.body(Problem.create() 
				.withTitle("Method not allowed") 
				.withDetail("You can't cancel an order that is in the " + order.getStatus() + " status"));
	}


}