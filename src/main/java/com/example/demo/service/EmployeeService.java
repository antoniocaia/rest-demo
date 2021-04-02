package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService extends BaseCrudService<EmployeeRepository, Employee, Long> {

	@Autowired
	public EmployeeService(EmployeeRepository repository) {
		super(repository);
	}
}
