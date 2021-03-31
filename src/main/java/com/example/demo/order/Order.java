package com.example.demo.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.employee.Employee;

import lombok.Data;
import lombok.NoArgsConstructor;

// Javax annotation
@Entity @Table(name = "CUSTOMER_ORDER")
@Data @NoArgsConstructor public class Order {

	private @Id @GeneratedValue Long id;
	private String description;
	private Status status;
	
	@ManyToOne
	private Employee employee;

	// Used only to preload
	Order(String description, Status status, Employee employee) {
		this.description = description;
		this.status = status;
		this.employee = employee;
	}
}