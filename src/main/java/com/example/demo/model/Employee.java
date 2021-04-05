package com.example.demo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "EMPLOYEES")
@NoArgsConstructor
@Getter @Setter
public class Employee extends IDModel<Long> {
	@Column(name = "NAME")
	private String name;
	@Column(name = "ROLE")
	private String role;
	// @OneToMany should always used with 'mappedBy' to avoid inconsistency.
	// Furthermore, the "many" entity ('Order' class) should have a @ManyToOne annotation without 'mappedBy'
	// https://www.baeldung.com/hibernate-one-to-many
	@OneToMany(mappedBy = "employee")
	private Set<CustomOrder> orders;

	// Used to add a relationship between Order and Employee
	public Employee(Long id) {
		setId(id);
	}

	// Only used in the loader
	public Employee(String name, String role, Set<CustomOrder> set) {
		super();
		this.name = name;
		this.role = role;
	}

	// Only used in the loader
	public Employee(long id, String name, String role) {
		this.id = id;
		this.name = name;
		this.role = role;
	}
}