package com.example.demo.employee;

class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4064880905761662401L;

	EmployeeNotFoundException(Long id) {
		super("Could not find employee " + id);
	}
}