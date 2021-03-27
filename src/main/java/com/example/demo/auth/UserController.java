package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping("/users")
	User newUser(@RequestBody User newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		return userRepository.save(newUser);
	}
	
	// Only for test
	@GetMapping("/users/{id}")
	User one(@PathVariable Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)); 
	}
}
