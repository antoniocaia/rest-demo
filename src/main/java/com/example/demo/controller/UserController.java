package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	// I should probably add a service layer
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/")
	User newUser(@RequestBody User newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		return userService.save(newUser);
	}

	@GetMapping("/")
	List<User> getAllUsers() {
		return userService.findAll();
	}

	@GetMapping("/{id}")
	User getUserById(@PathVariable Long id) {
		return userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}
}
