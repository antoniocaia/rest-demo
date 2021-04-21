package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "FREEDATA")
@NoArgsConstructor 
@AllArgsConstructor
@Getter @Setter
public class FreeData extends IDModel<Long> {
	@Column(name = "TITLE")
	private String title;
	@Column(name = "DATA")
	private String data;
}
