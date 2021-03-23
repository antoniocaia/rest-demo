package com.example.demo.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository extends CrudRepository
// @Repository not needed?
interface EmployeeRepository extends JpaRepository<Employee, Long> {}