package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Employee;

// JpaRepository extends CrudRepository
// @Repository not needed? SpringBoot automatically detect it because we are extending JpaRepository (that use @NoRepositoryBean)
// https://stackoverflow.com/questions/51918181/why-isnt-necessary-repository-for-this-spring-boot-web-app
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}