package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http.authorizeRequests().antMatchers("/orders/{id:[0-9]+}")
				.permitAll().anyRequest().authenticated()
			.and().formLogin().permitAll()
			.and().logout().permitAll()
			.and().httpBasic(); 
		//@formatter:on
	}
}
