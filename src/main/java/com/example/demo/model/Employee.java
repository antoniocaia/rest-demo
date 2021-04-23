package com.example.demo.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private String job;
	// @OneToMany should always used with 'mappedBy' to avoid inconsistency.
	// Furthermore, the "many" entity ('Order' class) should have a @ManyToOne annotation without 'mappedBy'
	// https://www.baeldung.com/hibernate-one-to-many
	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE)
	private Set<CustomOrder> orders;

	
	// Used inside the loader
	public Employee(String name, String job, Set<CustomOrder> orders) {
		super();
		this.name = name;
		this.job = job;
		this.orders = orders;
	}
	
	// For testing ...
	public Employee(long id, String name, String job) {
		this.id = id;
		this.name = name;
		this.job = job;
	}
}