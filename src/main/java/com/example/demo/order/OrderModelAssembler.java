package com.example.demo.order;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

	@Override
	public EntityModel<Order> toModel(Order order) {

		// Unconditional links to single-item resource and aggregate root
		EntityModel<Order> orderModel = EntityModel.of(
				order, 
				linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(), 
				linkTo(methodOn(OrderController.class).all()).withRel("orders"));
		
		// This is about decoupling: the EntityModel's links (the following allowed actions) are built based on the current status of the Order by the server, so the client doesn't have this responsibility 
		// Conditional links based on state of the order
		if (order.getStatus() == Status.IN_PROGRESS) {
			orderModel.add(linkTo(methodOn(OrderController.class).cancel(order.getId())).withRel("cancel"));
			orderModel.add(linkTo(methodOn(OrderController.class).complete(order.getId())).withRel("complete"));
		}

		return orderModel;
	}
}