package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CustomOrder;
import com.example.demo.repository.OrderRepository;

@Service
public class CustomOrderService extends BaseCrudService<OrderRepository, CustomOrder, Long> {

	@Autowired
	public CustomOrderService(OrderRepository repository) {
		super(repository);
	}
	
	public List<CustomOrder> findByEmployeeId(Long id) {
		return repository.findByEmployeeId(id);
	}
}
