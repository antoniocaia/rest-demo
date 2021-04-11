package com.example.demo.jwt;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtSecretKey {

	// private String secretString = "";

	@Bean
	public SecretKey getSecretKey() {
		// return Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

		// Auto-generate the signing key for the specified algorithm
		return Keys.secretKeyFor(SignatureAlgorithm.HS256); // We need to store this?

	}
}
