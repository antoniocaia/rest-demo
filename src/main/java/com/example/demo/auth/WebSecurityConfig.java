package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.jwt.JwtSecretKey;
import com.example.demo.jwt.JwtTokenValidationFilter;
import com.example.demo.jwt.JwtUsernamePasswordFilter;
import com.example.demo.model.RoleEnum;

// TODO Cercare come usare @PreAuthorize @RoleAllowed con enum come parametro
// TODO Come usare @EnableGlobalMethodSecurity

// https://www.marcobehler.com/guides/spring-security#_authentication_with_spring_security
// @EnableGlobalMethodSecurity
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// When implementing only one PasswordEncoder and UserDetailService we can avoid calling configureGlobal() and specify in the class the PE and UDS.
	// this works when Spring see the @EnableWebSecurity automatically search for a PasswordEncoder bean and UserDetailService bean.
	@Autowired
	@Qualifier("standardEncoder")
	PasswordEncoder passwordEncoder;
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	JwtSecretKey jwtSecretKey;

	// HttpBasic vs. FormLogin
	// Basic Authentication doen't use cookies, so every http request must send the user and password
	// Form-base Authentication use cookies to creates a session

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
			.csrf().disable() // csfr is needed when the client is a browser. Disable to send request form applications, like Postman
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(new JwtUsernamePasswordFilter(authenticationManager(), jwtSecretKey))
			.addFilterAfter(new JwtTokenValidationFilter(jwtSecretKey),JwtUsernamePasswordFilter.class)
			.authorizeRequests()
			.antMatchers("/login").permitAll()	// URLs white-list, no authentication required
			.antMatchers(HttpMethod.PUT, "/api/v1/orders/**", "/api/v1/employees/**").hasRole(RoleEnum.USER.getRole())
			.antMatchers(HttpMethod.POST, "/api/v1/orders/**", "/api/v1/employees/**").hasRole(RoleEnum.USER.getRole())
			.antMatchers(HttpMethod.DELETE, "/api/v1/orders/**", "/api/v1/employees/**").hasRole(RoleEnum.USER.getRole())
			.antMatchers(HttpMethod.GET, "/api/v1/orders/**", "/api/v1/employees/**").hasAnyRole(RoleEnum.USER.getRole(), RoleEnum.WATCHER.getRole())
			.antMatchers("/api/v1/users/**").hasRole(RoleEnum.ADMIN.getRole())
			.anyRequest().authenticated();
//			.and().httpBasic()  // TODO DISABLE THIS LINE. I keep this enabled only for postman
//			.and().formLogin();
//			.and().rememberMe()
//			.and().logout()		// This is automatically applied when using WebSecurityConfigurerAdapter. Clean eventual rememberMe. 
//			.clearAuthentication(true)
//			.invalidateHttpSession(true);
		//@formatter:on
	}

	// See top comment
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
}
