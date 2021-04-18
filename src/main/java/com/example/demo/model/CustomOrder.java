package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JsonIgnore
	@JoinColumn(name = "EMPLOYEE_ID", nullable = false)
	private Employee employee;

	public CustomOrder(String description, Status status, Employee employee) {
		super();
		this.description = description;
		this.status = status;
		this.employee = employee;
	}
	
	// TODO having a constructor only for test is bad practice 
	public CustomOrder(Long id, String description, Status status) {
		super.setId(id);
		this.description = description;
		this.status = status;
	}
}