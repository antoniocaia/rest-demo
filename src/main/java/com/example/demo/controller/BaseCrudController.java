package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.IDModel;
import com.example.demo.service.BaseCrudService;

public abstract class BaseCrudController<
		SERVICE extends BaseCrudService<REPOSITORY, MODEL, ID>, 
		REPOSITORY extends JpaRepository<MODEL, ID>,
		MODEL extends IDModel<ID>, 
		ID>
		extends AbstractController<SERVICE>
		implements ICrudController<MODEL, ID> {

	public BaseCrudController(SERVICE service) {
		this.service = service;
	}
	
	@Override
	@GetMapping("")
	public List<MODEL> getAll() {
		return service.findAll();
	}
	
	// To use Pageable, pass the arguments with the URL, like: "/page/?page=0&sort=id,desc"
	@Override
	@GetMapping("/page")
	public Page<MODEL> getAll(Pageable page) {
		return service.findAll(page);
	}

	@Override
	@GetMapping("/{id}")
	public Optional<MODEL> getById(@PathVariable ID id) {
		return service.findById(id);
	}

	@Override
	@PostMapping("")
	public MODEL add(@RequestBody MODEL model) {
		return service.save(model);
	}

	@Override
	@PutMapping("/{id}")
	public MODEL update(@RequestBody MODEL model, @PathVariable ID id) {
		return service.update(model, id);
	}

	@Override
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable ID id) {
		service.deleteById(id);
	}

	@Override
	@DeleteMapping("")
	public void deleteItem(@RequestBody MODEL model) {
		service.delete(model);
	}
}
