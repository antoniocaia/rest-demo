package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.FreeData;

public interface FreeDataRepository  extends JpaRepository<FreeData, Long>{

}
