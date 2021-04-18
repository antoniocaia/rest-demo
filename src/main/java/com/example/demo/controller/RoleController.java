package com.example.demo.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.RoleNotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.RoleService;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController extends BaseCrudController<RoleService, Role, Long> {

	public RoleController(RoleService service) {
		super(service);
	}
	
	@GetMapping("/{role}/users")
	public Set<User> getUsers(@PathVariable String role ){
		Role r = service.findByRole(role).orElseThrow(() -> new RoleNotFoundException(null));
		return r.getUsers();
	}
}
