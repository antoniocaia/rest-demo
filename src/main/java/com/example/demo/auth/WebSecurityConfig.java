package com.example.demo.auth;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// https://www.marcobehler.com/guides/spring-security#_authentication_with_spring_security
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final int encodeStrenght = 10; // Default value is 10
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http.authorizeRequests()
			.antMatchers("/users/**").hasAuthority("ROLE_ADMIN")
			.antMatchers("/employees/**", "/orders/**").hasAuthority("ROLE_USER")
			.anyRequest().authenticated()
			.and().formLogin()
			.and().httpBasic(); 
		//@formatter:on
	}
	
	// We need to specify the hashing algorithm for the passwords
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		// We specify the algorithm "strength" (that result in 2^strength iterations) and the salting function
		// https://docs.oracle.com/javase/6/docs/api/java/security/SecureRandom.html
		return new BCryptPasswordEncoder(encodeStrenght, new SecureRandom());
	}
}
