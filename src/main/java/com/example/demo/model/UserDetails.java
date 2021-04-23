package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "USERDETAILS")
@NoArgsConstructor 
@AllArgsConstructor
@Getter @Setter
public class UserDetails extends IDModel<Long>{
	
	@Column(name = "NAME")
	private String name;
	@Column(name = "LASTNAME")
	private String lastName;
	
	@JsonIgnore
	@OneToOne(mappedBy = "userDetails", cascade = CascadeType.MERGE)
	private User user;
	
	public UserDetails(String name, String lastName) {
		this.name = name;
		this.lastName = lastName;
	}
}
