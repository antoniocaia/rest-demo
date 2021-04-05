package com.example.demo.exception;

public class CustomOrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4037043112923941410L;

	public CustomOrderNotFoundException(Long id) {
		super("Could not find order " + id);
	}
}
