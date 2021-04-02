package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.repository.UserRepository;

import com.example.demo.model.User;

public class UserService extends BaseCrudService<UserRepository, User, Long> {

	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}
}
