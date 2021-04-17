package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICrudService<MODEL, ID> extends IService{

	Optional<MODEL> findById(ID id);

	List<MODEL> findAll();
	
	Page<MODEL> findAll(Pageable page);

	MODEL save(MODEL model);

	void delete(MODEL model);

	void deleteById(ID id);

	MODEL update(MODEL model);

	MODEL update(MODEL model, ID id);
}
