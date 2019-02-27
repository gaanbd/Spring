package com.techmango.springdataesmysql.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techmango.springdataesmysql.model.UserDetails;
import com.techmango.springdataesmysql.repository.UserDetailsRepository;
import com.techmango.springdataesmysql.repository.es.UserDetailsElasticsearchRepository;

import javassist.NotFoundException;

@Service
public class UserDetailsService {
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Autowired
	private UserDetailsElasticsearchRepository userDetailsElasticsearchRepository;

	public UserDetails createUserDetails(UserDetails userDetails) {
		UserDetails newUserDetails = null;
		if (Objects.nonNull(userDetails)) {
			newUserDetails = userDetailsRepository.save(userDetails);
			System.out.println("Saved data to MySql successfully");
			if (Objects.isNull(newUserDetails)) {
				System.out.println("Error while saving data to MySql");
			} else {
				newUserDetails = userDetailsElasticsearchRepository.save(userDetails);
				System.out.println("Saved into elasticsearch successfully");
				if (Objects.isNull(newUserDetails)) {
					System.out.println("Error while saving into elasticsearch");
				}
			}
		}
		return newUserDetails;
	}

	public UserDetails getUserById(int id) throws NotFoundException {
		return userDetailsRepository.findById(id).orElseThrow(() -> new NotFoundException("Data is empty for the id " + id ));
	}

	public Iterable<UserDetails> getAllUsers() {
		return userDetailsElasticsearchRepository.findAll();
	}

}
