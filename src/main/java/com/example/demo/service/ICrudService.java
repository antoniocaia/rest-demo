package com.example.demo.service;

import java.util.List;
import java.util.Optional;

public interface ICrudService<MODEL, ID> {

	Optional<MODEL> findById(ID id);

	List<MODEL> findAll();

	MODEL save(MODEL model);

	void delete(MODEL model);

	void deleteById(ID id);

	MODEL update(MODEL model);
}
