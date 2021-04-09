package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.service.IService;

// This class represent a generic controller that accept a generic service and define a logger. That is, nothing more.
// This can useful if you have to implement different kind of controllers that don't use CRUD service

public abstract class AbstractController <SERVICE extends IService> {
	
	protected final static Logger log = LoggerFactory.getLogger(AbstractController.class);
	
	protected SERVICE service;
}
