package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.RoleEnum;

// TODO Cercare come usare @PreAuthorize @RoleAllowed con enum come parametro e come usare @EnableGlobalMethodSecurity

// https://www.marcobehler.com/guides/spring-security#_authentication_with_spring_security
// @EnableGlobalMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// When implementing only one PasswordEncoder and UserDetailService we can avoid calling configureGlobal() and specify in the class the PE and UDS.
	// this works when Spring see the @EnableWebSecurity automatically search for a PasswordEncoder bean and UserDetailService bean. 
	//@Qualifier("standardEncoder")
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserDetailsService userDetailsService;
	
	// HttpBasic vs. FormLogin
	// Basic Authentication doen't use cookies, so every http request mu	st send user and password
	// Form-base Authentication use cookies to creates a session
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
			.csrf().disable() // ENABLE THIS LINE. Csfr is needed when the client is a browser. Disable to send request form applications, like Postman.
			.authorizeRequests()
			.antMatchers("/fakeUnprotectedUri").permitAll()	// URLs white-list, no authentication required
			.antMatchers(HttpMethod.PUT, "/api/v1/orders/**", "/api/v1/employees/**").hasRole(RoleEnum.USER.getRole())
			.antMatchers(HttpMethod.POST, "/api/v1/orders/**", "/api/v1/employees/**").hasRole(RoleEnum.USER.getRole())
			.antMatchers(HttpMethod.DELETE, "/api/v1/orders/**", "/api/v1/employees/**").hasRole(RoleEnum.USER.getRole())
			.antMatchers(HttpMethod.GET, "/api/v1/orders/**", "/api/v1/employees/**").hasAnyRole(RoleEnum.USER.getRole(), RoleEnum.WATCHER.getRole())
			.antMatchers("/api/v1/users/**").hasRole(RoleEnum.ADMIN.getRole())
			.anyRequest().authenticated()
			.and().httpBasic()  // DISABLE THIS LINE. I keep this enabled only for postman, to test the API.
			.and().formLogin()
			.and().rememberMe()
			.and().logout()		// This is automatically applied when using WebSecurityConfigurerAdapter. Clean eventual rememberMe. 
			.clearAuthentication(true)
			.invalidateHttpSession(true);
		//@formatter:on
	}

	// See top comment
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

}
