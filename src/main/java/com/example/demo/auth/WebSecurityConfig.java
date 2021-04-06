package com.example.demo.auth;

import java.security.SecureRandom;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.RoleEnum;

// https://www.marcobehler.com/guides/spring-security#_authentication_with_spring_security
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final int BCP_STRENGTH = 10; // Default value is 10
	
	// TODO Cercare come usare @PreAuthorize @RoleAllowed con enum
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/fakeUnprotectedUri").permitAll()	// URLs white-list, no authentication required
			.antMatchers(HttpMethod.PUT, "/api/v1/orders/**", "/api/v1/employees/**").hasRole(RoleEnum.USER.getRole())
			.antMatchers(HttpMethod.POST, "/api/v1/orders/**", "/api/v1/employees/**").hasRole(RoleEnum.USER.getRole())
			.antMatchers(HttpMethod.DELETE, "/api/v1/orders/**", "/api/v1/employees/**").hasAuthority(RoleEnum.USER.getRole())
			.antMatchers(HttpMethod.GET, "/api/v1/orders/**", "/api/v1/employees/**").hasAnyRole(RoleEnum.USER.getRole(), RoleEnum.WATCHER.getRole())
			.antMatchers("/api/v1/users/**").hasRole(RoleEnum.ADMIN.getRole())
			.anyRequest().authenticated().and().httpBasic() 
			.and().formLogin();	// Support for form login, generate one if not specified 
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
