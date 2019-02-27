package com.techmango.ehcache.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techmango.ehcache.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>{

	List<Company> findByName(String name);
	
	}
