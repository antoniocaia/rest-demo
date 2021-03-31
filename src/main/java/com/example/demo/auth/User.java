package com.example.demo.auth;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor public class User {
	private @Id @GeneratedValue Long id;
	private String username;
	private String password;
	private String role;
	
	User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}
}
