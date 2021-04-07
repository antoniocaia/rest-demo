package com.example.demo.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "USERS")
@NoArgsConstructor 
@Setter @Getter 
public class User extends IDModel<Long>{
	@Column(name = "USERNAME")
	private String username;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "EXPIRED")
	private boolean expired;
	@Column(name = "LOCKED")
	private boolean locked;
	@Column(name = "ENABLED")
	private boolean enabled;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<Role> roles;
	
	public User(String username, String password, Set<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	
	public User(String username, String password, Set<Role> roles, boolean expired, boolean locked, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.expired = expired;
		this.locked = locked;
		this.enabled = enabled;
	}
}
