package com.example.demo.security;

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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http.authorizeRequests()
			.antMatchers("/users/*").hasAuthority("ROLE_ADMIN")
			.antMatchers("/employees/**").hasAuthority("ROLE_USER")
			.anyRequest().authenticated()
			.and().formLogin()
			.and().httpBasic(); 
		//@formatter:on
	}
	
	// 'UserDetailsService' is used by Spring to retrieve users informations.
	// it define a method 'loadUserByUsername()' that we override to customize the process to retrieve the users
	/*
	@Bean("userDatailService")
	public UserDetailsService userDetailsService() {
		return new UserService();
	}
	*/
	
	// We need to specify the hashing alghoritm for the passwords
	// For now I'm using default spring alghoritm
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
