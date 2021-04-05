package com.example.demo.auth;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// https://www.marcobehler.com/guides/spring-security#_authentication_with_spring_security
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final int BCP_STRENGTH = 10; // Default value is 10

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http.authorizeRequests()
			.antMatchers("/unprotectedUrl").permitAll() // URLs white-list, no authentication required
			.antMatchers("/api/v1/users/**").hasAuthority("ROLE_ADMIN")	// URLs that require role 'ADMIN' 
			.antMatchers("/api/v1/employees/**", "/api/v1/orders/**").hasAuthority("ROLE_USER") // URLs that require role 'USER'
			.anyRequest().authenticated()
			//.and().formLogin()	// Support for form login, generate one if not specified
			.and().httpBasic(); 
		//@formatter:on
	}

	// We need to specify the hashing algorithm for the passwords
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		// We specify the algorithm "strength" (that result in 2^strength iterations) and the salting function
		// https://docs.oracle.com/javase/6/docs/api/java/security/SecureRandom.html
		return new BCryptPasswordEncoder(BCP_STRENGTH, new SecureRandom());
	}
}
