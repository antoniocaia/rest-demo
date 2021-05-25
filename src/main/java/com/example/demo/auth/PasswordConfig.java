package com.example.demo.auth;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordConfig {
	
	private static final int BCP_STRENGTH = 10; // Default value is 10
	//private static final int BCP_SUPER_STRENGTH = 16; // Default value is 10

	// We need to specify the hashing algorithm for the passwords
	@Bean("standardEncoder")
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		// We specify the algorithm "strength" (that result in 2^strength iterations) and the salting function
		// https://docs.oracle.com/javase/6/docs/api/java/security/SecureRandom.html
		return new BCryptPasswordEncoder(BCP_STRENGTH, new SecureRandom());
	}

	// Educational purpose only!
	//	@Bean("strongEncoder")
	//	public BCryptPasswordEncoder superStrongPasswordEncoder() {
	//		return new BCryptPasswordEncoder(BCP_SUPER_STRENGTH, new SecureRandom());
	//	}
}
