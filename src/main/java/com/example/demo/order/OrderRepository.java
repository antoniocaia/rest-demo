package com.example.demo.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<Order, Long> {

	public List<Order> findByEmployeeId(long id);
}