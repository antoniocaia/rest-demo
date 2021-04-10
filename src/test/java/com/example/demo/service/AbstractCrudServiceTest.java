package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.IDModel;

public class AbstractCrudServiceTest<SERVICE extends BaseCrudService<REPOSITORY, MODEL, ID>, REPOSITORY extends JpaRepository<MODEL, ID>, MODEL extends IDModel<ID>, ID> {
	
	SERVICE service;
	REPOSITORY repository;
	
	// You can't really test anything with this approach imo, and the test are not going to be "unit"
}
