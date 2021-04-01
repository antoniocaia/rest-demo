package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository repository;

	public EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}

	public Employee getEmployee(long id) {
		return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	}

	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	public Employee saveEmployee(Employee employee) {
		return repository.save(employee);
	}

	public Employee updateEmployee(Employee newEmployee, long id) {
		return repository.findById(id).map(e -> {
			e.setName(newEmployee.getName());
			e.setRole(newEmployee.getRole());
			return repository.save(e);
		}).orElseGet(() -> {
			newEmployee.setId(id);
			return repository.save(newEmployee);
		});
	}

	public void deleteEmployee(long id) {
		repository.delete(repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id)));
	}
}