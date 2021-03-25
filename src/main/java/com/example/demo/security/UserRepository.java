package com.example.demo.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long>{ 
	
	public Optional<User> findByUsername(String name);
}
