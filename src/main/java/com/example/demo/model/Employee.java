package com.example.demo.model;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Employee extends IDModel<Long> {

	private String name;
	private String role;
	// @OneToMany should always used with 'mappedBy' to avoid inconsistency.
	// Furthermore, the "many" entity ('Order' class) should have a @ManyToOne annotation without 'mappedBy'
	// https://www.baeldung.com/hibernate-one-to-many
	
	// @OneToMany(mappedBy = "employee")
	// private Set<Order> orders;

	public Employee(String name, String role) {
		super();
		this.name = name;
		this.role = role;
	}

	public Employee(Long id) {
		setId(id);
	}
}