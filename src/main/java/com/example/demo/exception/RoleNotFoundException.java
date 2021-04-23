package com.example.demo.exception;

public class RoleNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3321007582022939470L;

	public RoleNotFoundException(String role) {
		super("Could not find role " + role);
	}
}