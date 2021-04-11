package com.example.demo.jwt;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtSecretKeySecretString {

	@Autowired
	private JwtConfig jwtConfig;

	// @Bean Commenting this line so there is no collision with the actual bean
	public SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtConfig.getSecretString()));
	}
}
