package com.example.demo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "ROLES")
@NoArgsConstructor 
@Setter @Getter 
public class Role extends IDModel<Long>{
	@Column(name = "ROLE")
	private String role;
	@Column(name = "DESCRIPTION")
	private String description;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private Set<User> users;
	
	public Role(String role, String description, Set<User> users) {
		super();
		this.role = role;
		this.description = description;
		this.users = users;
	}
}
