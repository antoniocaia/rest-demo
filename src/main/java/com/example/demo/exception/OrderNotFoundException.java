package com.example.demo.exception;

public class OrderNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4037043112923941410L;

	public OrderNotFoundException(Long id) {
		super("Could not find order " + id);
	}
}
