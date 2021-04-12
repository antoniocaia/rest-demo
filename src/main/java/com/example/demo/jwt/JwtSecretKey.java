package com.example.demo.jwt;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtSecretKey {

	private SecretKey secretKey;

	public JwtSecretKey() {
		// Auto-generate the signing key for the specified algorithm
		secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Length of the key determinate the algorithm that will be used
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}
}
