package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.OrderNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository repository;

	public OrderService(OrderRepository repository) {
		this.repository = repository;
	}

	public Order getOrder(long id) {
		return repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
	}
	
	public List<Order> getOrdersByEmployee(long id) {
		return repository.findByEmployeeId(id);
	}
	
	public List<Order> getAllOrders() {
		return repository.findAll();
	}

	public Order saveOrder(Order order) {
		return repository.save(order);
	}

	void deleteOrder(long id) {
		repository.delete(repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id)));
	}
}
