package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

public interface ICrudController<MODEL, ID> {

	public List<MODEL> getAll();

	public Optional<MODEL> getById(ID id);

	public MODEL add(MODEL model);

	public MODEL update(MODEL model, ID id);

	public void deleteById(ID id);

	public void deleteItem(MODEL model);
}
