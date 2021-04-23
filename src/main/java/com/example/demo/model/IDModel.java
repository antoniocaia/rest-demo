package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

// @MappedSuperclass let the class that extends IDModel use 'id' as a identifier
@MappedSuperclass
public class IDModel<ID> {

	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY) // With GenerationType.IDENTITY each table independently increment their ID
	@Column(name = "ID", unique = true)
	@Getter @Setter ID id;
}
