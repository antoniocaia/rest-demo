package com.example.demo.exception;

public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4064880905761662401L;

	public <ID> EmployeeNotFoundException(ID id) {
		super("Could not find employee " + id);
	}
}