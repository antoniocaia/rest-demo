package com.example.demo.controller.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface IModelAssemblerCrudController<MODEL, ID> {

	public CollectionModel<EntityModel<MODEL>> getAll();

	public EntityModel<MODEL> getById(ID id);

	public ResponseEntity<?> add(MODEL model);

	public EntityModel<MODEL> update(MODEL model, ID id);

	public ResponseEntity<?> deleteById(ID id);

	public void deleteItem(MODEL model);
}
