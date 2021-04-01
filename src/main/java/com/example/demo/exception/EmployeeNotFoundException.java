package com.example.demo.exception;

public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4064880905761662401L;

	public EmployeeNotFoundException(Long id) {
		super("Could not find employee " + id);
	}
}