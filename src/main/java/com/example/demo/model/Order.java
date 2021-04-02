package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "CUSTOMER_ORDER")
@NoArgsConstructor
@Getter @Setter
public class Order extends IDModel<Long>{

	private String description;
	private Status status;
	
	@ManyToOne
	private Employee employee;

	public Order(String description, Status status, Employee employee) {
		super();
		this.description = description;
		this.status = status;
		this.employee = employee;
	}
}