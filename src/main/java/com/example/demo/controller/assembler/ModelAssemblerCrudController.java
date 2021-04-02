package com.example.demo.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.IDModel;
import com.example.demo.service.BaseCrudService;

public abstract class ModelAssemblerCrudController<SERVICE extends BaseCrudService<? extends JpaRepository<MODEL, ID>, MODEL, ID>, ASSEMBLER extends RepresentationModelAssembler<MODEL, EntityModel<MODEL>>, MODEL extends IDModel<ID>, ID>
		implements IModelAssemblerCrudController<MODEL, ID> {

	protected SERVICE service;
	protected ASSEMBLER assembler;
	
	public ModelAssemblerCrudController(SERVICE service, ASSEMBLER assembler) {
		this.service = service;
		this.assembler = assembler;
	}
		
	// CollectionModel<EntityModel<T>> is a container of EntityModel<T> used by HATEOAS
	@Override
	@GetMapping("/")
	public CollectionModel<EntityModel<MODEL>> getAll() {
		List<EntityModel<MODEL>> list = service.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
		return CollectionModel.of(list, linkTo(methodOn(ModelAssemblerCrudController.class).getAll()).withSelfRel());
	}

	// EntityModel<T> is a generic container from Spring HATEOAS that includes not only the data but a collection of links
	@Override
	@GetMapping("/{id}")
	public EntityModel<MODEL> getById(@PathVariable ID id) {
		return assembler.toModel(service.findById(id).orElseThrow(() -> new EntityNotFoundException()));
	}

	@Override
	@PostMapping("/")
	public ResponseEntity<?> add(@RequestBody MODEL model) {
		EntityModel<MODEL> entityModel = assembler.toModel(service.save(model));
		// '.created()' generate a "HTTP 201 Created" status. 'created' expect the location of the new instance as a parameter
		// To get the URI, we use the link stored in the EntityModel under the reference 'self'
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) // code + location
				.body(entityModel); // body of the HTTP response
	}

	// ResponseEntity<T> is used to represent an HTTP response
	@Override
	@PutMapping("/{id}")
	public EntityModel<MODEL> update(@RequestBody MODEL model, @PathVariable ID id) {
		return assembler.toModel(service.update(model, id));
	}

	// @PathVariable bound the parameter 'Long id' to the URI template whit the SAME NAME
	// So if the URI is "/employees/{paperino}", we have to pass to the method the parameter '@PathVariable Long paperino'
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable ID id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	// TODO: double check this method
	@Override
	@DeleteMapping("/")
	public void deleteItem(@RequestBody MODEL model) {
		// TODO Auto-generated method stub
	}

}
