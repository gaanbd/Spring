package com.techmango.ehcache.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techmango.ehcache.model.Company;
import com.techmango.ehcache.service.impl.CompanyService;

@RestController
@RequestMapping(value="/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;
	
	@PostMapping(value="/save")
	public ResponseEntity<Company> saveCompany(@RequestBody Company company){
		companyService.saveCompany(company);
		return new ResponseEntity<>(company, HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllCompanies")
	public ResponseEntity<List<Company>> getAllCompanies(){
		return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
	}
	
	@GetMapping(value="/getCompany/{id}")
	public ResponseEntity<Optional<Company>> getCompany(@PathVariable("id")Integer id){
		return new ResponseEntity<>(companyService.findCompanyById(id), HttpStatus.OK);
	}
	
	@GetMapping(value="/getCompanyByName/{name}")
	public ResponseEntity<List<Company>> getCompanyByName(@PathVariable("name")String name){
		return new ResponseEntity<>(companyService.findCompanyByName(name), HttpStatus.OK);
	}
}
