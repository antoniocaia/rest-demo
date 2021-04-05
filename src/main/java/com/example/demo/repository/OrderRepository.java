package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CustomOrder;

public interface OrderRepository extends JpaRepository<CustomOrder, Long> {
	
	// The body of this method is actually auto generated. 
	// Following this convention (https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)
	// it's possible to define method only specifying their name
	public List<CustomOrder> findByEmployeeId(long id);
}