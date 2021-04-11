package com.example.demo.jwt;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtSecretKey {

	@Bean
	public SecretKey getSecretKey() {
		// Auto-generate the signing key for the specified algorithm
		return Keys.secretKeyFor(SignatureAlgorithm.HS512); // Length of the key determinate the algorithm that will be used
	}
}
