package com.example.demo.controller;

import java.util.List;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.CustomOrderNotFoundException;
import com.example.demo.model.CustomOrder;
import com.example.demo.model.Status;
import com.example.demo.repository.CustomOrderRepository;
import com.example.demo.service.CustomOrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class CustomOrderController extends BaseCrudController<CustomOrderService, CustomOrderRepository, CustomOrder, Long> {

	public CustomOrderController(CustomOrderService service) {
		super(service);
	}
	
	@GetMapping("/completed")
	public List<CustomOrder> getColpetedOrders() {
		return service.findByStatus(Status.COMPLETED);
	}
	
	@GetMapping("/inprogress")
	public List<CustomOrder> getInProgressOrders() {
		return service.findByStatus(Status.IN_PROGRESS);
	}
	
	@PutMapping("/{id}/complete")
	public ResponseEntity<?> completeOrder(@PathVariable Long id) {
		CustomOrder order = service.findById(id).orElseThrow(() -> new CustomOrderNotFoundException(id));

		// You can "complete" an Order only if its precedent status was IN_PROGRESS
		// Check if the Order status is IN_PROGRESS, then return an OK response
		if (order.getStatus() == Status.IN_PROGRESS) {
			order.setStatus(Status.COMPLETED);
			return ResponseEntity.ok(service.update(order)); // 'ok()' is for status "HTTP 200 OK"
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
		CustomOrder customOrder = service.findById(id).orElseThrow(() -> new CustomOrderNotFoundException(id));

		if (customOrder.getStatus() == Status.IN_PROGRESS) {
			customOrder.setStatus(Status.CANCELLED);
			return ResponseEntity.ok(service.update(customOrder));
		}

		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
				.body(Problem.create().withTitle("Method not allowed")
						.withDetail("You can't cancel an order that is in the " + customOrder.getStatus() + " status"));
	}
}
