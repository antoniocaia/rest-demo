package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService extends BaseCrudService<UserRepository, User, Long> {

	@Autowired
	public UserService(UserRepository repository) {
		super(repository);
	}
	
	public Optional<User> findByUserName(String username) {
		return repository.findByUsername(username);
	}
}
