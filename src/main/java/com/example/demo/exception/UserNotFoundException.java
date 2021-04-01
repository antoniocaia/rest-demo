package com.example.demo.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4064880905761662401L;

	public UserNotFoundException(Long id) {
		super("Could not find user " + id);
	}
}