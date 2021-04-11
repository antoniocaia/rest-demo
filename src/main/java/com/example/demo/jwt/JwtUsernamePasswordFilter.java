package com.example.demo.jwt;

import java.time.LocalDate;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
	
	@Autowired 
	JwtSecretKey secretKey;
	
//	// The professor didn't changed this method at all???
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException {
//		// Re-using parent implementation
//		String username = obtainUsername(request);
//		username = (username != null) ? username : "";
//		username = username.trim();
//		String password = obtainPassword(request);
//		password = (password != null) ? password : "";
//		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//		return this.getAuthenticationManager().authenticate(authRequest);
//	}

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
				.signWith(secretKey.getSecretKey())
				.compact();
		// @formatter:on
		
		// We add in the response a header containing the token. The client shoul send back this token every time it makes an HTTP request
		response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token); 
	}
	
	
}
