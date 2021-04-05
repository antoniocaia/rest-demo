package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "ORDERS")
@NoArgsConstructor
@Getter @Setter
public class CustomOrder extends IDModel<Long>{
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "STATUS")
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;

	public CustomOrder(String description, Status status, Employee employee) {
		super();
		this.description = description;
		this.status = status;
		this.employee = employee;
	}

	public CustomOrder(String description, Status status) {
		super();
		this.description = description;
		this.status = status;
	}
}