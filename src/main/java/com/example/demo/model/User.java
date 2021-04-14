package com.example.demo.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "USERS")
@NoArgsConstructor 
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) // When comparing instances, we don't want to take in consideration the ID (unique value), so this is correct
@Setter @Getter 
public class User extends IDModel<Long>{
	
	@Column(name = "USERNAME", nullable = false)
	private String username;
	@Column(name = "PASSWORD", nullable = false)
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
	
//  // Not used at all?	
//	public User(String username, String password, Set<Role> roles) {
//		super();
//		this.username = username;
//		this.password = password;
//		this.roles = roles;
//	}
	
	// Used only in the loader?
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
