package com.example.demo.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
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
}
