package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "ROLES")
@NoArgsConstructor 
@Setter @Getter 
public class Role extends IDModel<Long>{
	private String role;
	private String description;
}
