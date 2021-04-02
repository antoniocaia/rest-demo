package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService extends BaseCrudService<EmployeeRepository, Employee, Long> {

	@Autowired
	public EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}

	public Employee update(Employee employee, Long id) {
		return repository.findById(id).map(e -> {
			e.setName(employee.getName());
			e.setRole(employee.getRole());
			return repository.save(e);
		}).orElseThrow(() -> new EmployeeNotFoundException(id));
	}
}
