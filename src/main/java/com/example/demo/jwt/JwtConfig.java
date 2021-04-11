package com.example.demo.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application.jwt")
public class JwtConfig {

	private String secretString;
	
	public String getSecretString() {
		return secretString;
	}

	public void setSecretString(String secretString) {
		this.secretString = secretString;
	}
}