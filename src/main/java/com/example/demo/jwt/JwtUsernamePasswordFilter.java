package com.example.demo.jwt;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.model.Credentials;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

/*
 *  https://github.com/jwtk/jjwt#quickstart
 *  The compact representation of a signed JWT is a string that has three parts, each separated by a '.'
 *  The first part is the header, which at a minimum needs to specify the algorithm used to sign the JWT. 
 *  The second part is the body. This part has all the claims of this JWT encoded in it. 
 *  The final part is the signature. It's computed by passing a combination of the header and body through the algorithm specified in the header.
 */

// Before a HTTP request reach the API, we can do some controls over it. If you don't want to use the default authentication methods and related filters
// (httpBasic, formLogin, etc) we can specify our own filters. 
// In this filter we want to manage username and password during a login attempt (using the parent method 'attemptAuthentication' should be fine)
// and generate our own token with JWT to "remember" the user
// TODO I'm going to still use loginForm after this?
public class JwtUsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {

	// Why I can't use Autowired in this filter?
	// Because this class is used by WebSecurityConfig, and AuthenticationManager is supplied by WebSecurityConfig, so there is a circular dependency.
	private AuthenticationManager authenticationManager;
	// Also, for some reason you can't inject directly in the filter without additional configuration
	private JwtSecretKey jwtSecretKey;

	public JwtUsernamePasswordFilter(AuthenticationManager authenticationManager, JwtSecretKey jwtSecretKey) {
		this.authenticationManager = authenticationManager;
		this.jwtSecretKey = jwtSecretKey;
	}

	// Basically the parent method but we need to redefine the authenticationManager
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username;
		String password;

		try {
			Credentials credentials = new ObjectMapper().readValue(request.getInputStream(), Credentials.class);
			username = credentials.getUsername();
			password = credentials.getPassword();
		} catch (IOException e) {
			e.printStackTrace();
			username = "";
			password = "";
		}
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		return authenticationManager.authenticate(authRequest);
	}

	// When we are correctly authenticated we want to generate a token to recognize the user using JWT
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) {
		// JWT claims are properties of the tokens that we are going to use for authentication
		// @formatter:off
		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim("roles", authResult.getAuthorities())
				.setIssuedAt(java.sql.Date.valueOf(LocalDate.now()))
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(1)))
				.signWith(jwtSecretKey.getSecretKey())
				.compact();
		// @formatter:on

		// We add in the response a header containing the token. The client shoul send back this token every time it makes an HTTP request
		response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
	}

}
