package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public class BaseCrudService<REPO extends JpaRepository<MODEL, ID>, MODEL, ID> implements ICrudService<MODEL, ID> {

	protected REPO repository;

	@Override
	public Optional<MODEL> findById(ID id) {
		return repository.findById(id);
	}

	@Override
	public List<MODEL> findAll() {
		return repository.findAll();
	}

	@Override
	public MODEL save(MODEL model) {
		return repository.save(model);
	}

	@Override
	public void delete(MODEL model) {
		repository.delete(model);
	}

	@Override
	public void deleteById(ID id) {
		repository.deleteById(id);
	}

	@Override
	public MODEL update(MODEL model) {
		return repository.save(model);
	}
}