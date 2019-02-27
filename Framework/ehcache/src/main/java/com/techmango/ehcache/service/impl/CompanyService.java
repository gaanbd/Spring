package com.techmango.ehcache.service.impl;

import java.util.List;
import java.util.Optional;

import com.techmango.ehcache.model.Company;

public interface CompanyService {

	List<Company> getAllCompanies();
	
	Company saveCompany(Company company);
	
	Optional<Company> findCompanyById(Integer id);

	List<Company> findCompanyByName(String name);

	Company updateCompany(Company company);

}
