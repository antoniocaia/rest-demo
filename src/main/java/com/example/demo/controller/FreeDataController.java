package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.FreeData;
import com.example.demo.repository.FreeDataRepository;
import com.example.demo.service.FreeDataService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/free")
public class FreeDataController extends BaseCrudController<FreeDataService, FreeDataRepository, FreeData, Long>{

	@Autowired
	public FreeDataController(FreeDataService service) {
		super(service);
	}
}
