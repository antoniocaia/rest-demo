package com.example.demo.model;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor 
@Setter @Getter 
public class User extends IDModel<Long>{
	private String username;
	private String password;
	private String role;
	
	public User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
}
