package com.example.demo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

// @MappedSuperclass let the class that extends IDModel use 'id' as a identifier
@MappedSuperclass
public class IDModel<ID> {

	@Id
	@GeneratedValue
	@Getter @Setter ID id;
}
