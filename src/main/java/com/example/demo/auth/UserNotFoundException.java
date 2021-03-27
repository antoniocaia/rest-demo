package com.example.demo.auth;

class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4064880905761662401L;

	UserNotFoundException(Long id) {
		super("Could not find user " + id);
	}
}