package com.example.demo.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/json")
public class UserController extends BaseCrudController<UserService, User, Long> {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserController(UserService userService) {
		super(userService);
	}
	
	@Override
	@PostMapping("/")
	public User add(@RequestBody User newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		return service.save(newUser);
	}

	@Override
	@PutMapping("/{id}")
	public User update(@RequestBody User newUser, @PathVariable Long id) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		return service.update(newUser, id);
	}
	
	@GetMapping("/{id}/roles")
	public Set<Role> getRoles(@PathVariable Long id){
		User user =  service.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		return user.getRoles();
	}
	
	// TODO Patch??? miglior approccio?
	@PatchMapping("/{id}/lock/{lock}")
	public User setLock(@PathVariable Long id, @PathVariable boolean lock) {
		User user =  service.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		user.setLocked(lock);
		return service.update(user);
	}
	
	// TODO Add controller for enabled and expired
}
