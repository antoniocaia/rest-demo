package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.FreeData;
import com.example.demo.repository.FreeDataRepository;

@Service
public class FreeDataService  extends BaseCrudService<FreeDataRepository, FreeData, Long>{

	@Autowired
	public FreeDataService(FreeDataRepository repository) {
		super(repository);
	}
}
