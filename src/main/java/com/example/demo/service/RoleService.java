package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;

@Service
public class RoleService  extends BaseCrudService<RoleRepository, Role, Long> {

	@Autowired
	public RoleService(RoleRepository repository) {
		super(repository);
	}
	
	public Optional<Role> findByRole(String role){
		return repository.findByRole(role);
	}
}