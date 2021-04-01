package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor public class Employee {
	
	private @Id @GeneratedValue Long id;
	private String name;
	private String role;
	// @OneToMany should always used with 'mappedBy' to avoid inconsistency. 
	// Furthermore, the "many" entity ('Order' class) should have a @ManyToOne annotation without 'mappedBy'
	// https://www.baeldung.com/hibernate-one-to-many
	// @OneToMany(mappedBy = "employee")	
	// private Set<Order> orders;
	
	// Only used for loading resources
	public Employee(String name, String role) {
		this.name = name;
		this.role = role;
	}
	
	// Only used for loading resources
	public Employee(Long id) {	
		this.id = id;
	}

	// Only used for loading resources
	public Employee(Long id, String name, String role) {
		this.id = id;
		this.name = name;
		this.role = role;
	}
	
}