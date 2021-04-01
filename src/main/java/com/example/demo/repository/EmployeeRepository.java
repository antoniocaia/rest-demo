package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Employee;

// JpaRepository extends CrudRepository
// @Repository not needed? SpringBoot automatically detect it because we are extending JpaRepository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}