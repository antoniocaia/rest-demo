package com.example.demo.service;

import java.util.List;
import java.util.Optional;

public interface ICrudService<MODEL, ID> extends IService{

	Optional<MODEL> findById(ID id);

	List<MODEL> findAll();

	MODEL save(MODEL model);

	void delete(MODEL model);

	void deleteById(ID id);

	MODEL update(MODEL model);

	MODEL update(MODEL model, ID id);
}
