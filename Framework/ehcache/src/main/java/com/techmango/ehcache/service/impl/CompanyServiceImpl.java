package com.techmango.ehcache.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.techmango.ehcache.model.Company;
import com.techmango.ehcache.repository.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService{

	@Autowired
	CompanyRepository companyRepository;
	
	
	@CacheEvict(value="companies", allEntries=true)
	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	@CachePut(value="companies")
	@Override
	public Company saveCompany(Company company) {
		return companyRepository.save(company);
	}

	/*Avoid caching if i/p id==2 else cache all entries(unless refers to stop cache if this condition executes)*/
	@Override
	//@Cacheable(value="companies" , key="#id", unless="#id != 0 and #id==2")
	@Cacheable(value="companies" , key="#id", unless="#result != null and #result.name.toUpperCase().startsWith('TTS')")
	public Optional<Company> findCompanyById(Integer id) {
		return companyRepository.findById(id);
	}

	@Override
	@Cacheable(value="companies")
	public List<Company> findCompanyByName(String name) {
		return companyRepository.findByName(name);
	}
	
	@Override
	//@CachePut(value="companies")
	@Cacheable(value="companies")
	public Company updateCompany(Company company) {
		 if(Objects.nonNull(company.getId())) {
			 return companyRepository.save(company);
		 }
		 return null;
	}

}
