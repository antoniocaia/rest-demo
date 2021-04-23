package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICrudController<MODEL, ID> extends IController {

	public List<MODEL> getAll();
	
	public Page<MODEL> getAll(Pageable page);

	public Optional<MODEL> getById(ID id);

	public MODEL add(MODEL model);

	public MODEL update(MODEL model, ID id);

	public void deleteById(ID id);

	public void deleteItem(MODEL model);
}
