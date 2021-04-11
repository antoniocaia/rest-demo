package com.example.demo.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

// This filter manage the token 
public class JwtTokenValidationFilter extends OncePerRequestFilter {

	@Autowired
	JwtSecretKey secretKey;
	
	// For each incoming HTTP request we check the header for a token, if the token is present we check if there are any sign of tempering.
	// If the token is ok we take the information inside it and use them to authenticate the user
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && !authorizationHeader.isEmpty() && authorizationHeader.startsWith("Bearer ")) {
			String token = authorizationHeader.replace("Bearer ", "");
			try {
				Jws<Claims> jws = Jwts.parserBuilder()
						.setSigningKey(secretKey.getSecretKey())
						.build()
						.parseClaimsJws(token); // in addition to other controls/exceptions, parseClaimsJws check if the signature match and if not throws a 'SignatureException'

				// At this point we can safely trust the JWT, so we can read the claims
				String username = jws.getBody().getSubject();
				Set<GrantedAuthority> authorities = ((Collection<Map<String,String>>) jws.getBody().get("roles"))	// TODO is the casting forced?
						.stream()
						.map(m -> new SimpleGrantedAuthority(m.get("roles")))
						.collect(Collectors.toSet());

				Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities); 
				SecurityContextHolder.getContext().setAuthentication(auth);	// Created a new authorization, we save it in the security context so it can be used to check if the user is authenticated // TODO But how?

			} catch (JwtException ex) {
				// we *cannot* use the JWT as intended by its creator
				throw new IllegalStateException(String.format("Invalid token: %s", token));
			}
		}

		filterChain.doFilter(request, response); // Carry on the filter chain
	}

}
