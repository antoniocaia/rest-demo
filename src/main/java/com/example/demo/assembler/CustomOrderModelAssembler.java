package com.example.demo.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.demo.controller.assembler.CustomOrderController;
import com.example.demo.model.CustomOrder;
import com.example.demo.model.Status;

@Component
public class CustomOrderModelAssembler implements RepresentationModelAssembler<CustomOrder, EntityModel<CustomOrder>> {

	@Override
	public EntityModel<CustomOrder> toModel(CustomOrder customOrder) {

		// Unconditional links to single-item resource and aggregate root
		EntityModel<CustomOrder> orderModel = EntityModel.of(customOrder);
			//linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel(),
			//linkTo(methodOn(OrderController.class).getAll()).withRel("orders"));

		// This is about decoupling: the EntityModel's links (the following allowed actions) are built based on the current status of the Order by the server, so the client doesn't have this responsibility
		// Conditional links based on state of the order
		if (customOrder.getStatus() == Status.IN_PROGRESS) {
			orderModel.add(linkTo(methodOn(CustomOrderController.class).cancelOrder(customOrder.getId())).withRel("cancel"));
			orderModel.add(linkTo(methodOn(CustomOrderController.class).completeOrder(customOrder.getId())).withRel("complete"));
		}

		return orderModel;
	}
}