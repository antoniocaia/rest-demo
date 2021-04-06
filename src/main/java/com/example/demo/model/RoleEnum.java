package com.example.demo.model;

public enum RoleEnum {
	USER("USER"),
	ADMIN("ADMIN"),
	WATCHER("WATCHER");
	
	private final String role;

	RoleEnum(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
}	
