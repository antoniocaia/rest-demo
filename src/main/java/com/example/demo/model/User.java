package com.example.demo.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO check if I wan't to keep ID in IDModel or not
// When comparing instances, we don't want to take in consideration the ID (unique value), because it really doesn't "describe" the instance.
// Unfortunately, equals is used during authentication process, and it needs the complete equals that use the id too.
//@EqualsAndHashCode // Can't us it
@Entity @Table(name = "USERS")
@NoArgsConstructor 
@AllArgsConstructor
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

	/*
	 * When we have bidirectional relation we have to "ignore" at least one side:
	 * Having both side connected create a loop, so we can chose between:
	 * 1 - Ignore both end, so when we get an entity we don't get all the entities connected. 
	 * We can still access them with specific end-points. (I like this one the most)
	 * 2 - Ignore only one side. If we have A and B, and B "ignore" A, then when we get an A entity we get all its Bs, 
	 * but when we get a B we don't get any As.
	 */
	@JsonIgnore
	/*
	 * MERGE: I have A and B. If I take an A, take its Bs, and update them, 
	 * when I save A (merging the local instace with the persisten one) the Bs are updated too.
	 * 
	 * PERSIST: I have A and B, both not in the DB yet. I create A, and add B to it. 
	 * When I save A, the B is saved too
	 */
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	private Set<Role> roles;
	
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST })
	private UserDetails userDetails;
	
    // Not used at all?	
	public User(String username, String password, Set<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	
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
