package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderService extends BaseCrudService<OrderRepository, Order, Long> {

	@Autowired
	public OrderService(OrderRepository repository) {
		this.repository = repository;
	}
	
	public List<Order> findByEmployeeId(Long id) {
		return repository.findByEmployeeId(id);
	}
}
