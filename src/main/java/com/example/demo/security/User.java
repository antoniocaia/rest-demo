package com.example.demo.security;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	private @Id @GeneratedValue Long id;
	private String username;
	private String password;
	private String role;
	
	User() { }
	
	User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
		User employee = (User) o;
		return Objects.equals(this.id, employee.id) && Objects.equals(this.username, employee.username)
				&& Objects.equals(this.password, employee.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.username, this.password);
	}

	@Override
	public String toString() {
		return "User{" + "id=" + this.id + ", name='" + this.username + '\'' + ", password='" + this.password + '\''
				+ ", auth=" + role + '}';
	}
}
