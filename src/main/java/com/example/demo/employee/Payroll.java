package com.example.demo.employee;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository extends CrudRepository
// @Repository not needed? SpringBoot automatically detect it because we are extending JpaRepository
interface EmployeeRepository extends JpaRepository<Employee, Long> {}