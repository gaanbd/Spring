package com.techmango.springdataesmysql.web.rest;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techmango.springdataesmysql.model.UserDetails;
import com.techmango.springdataesmysql.service.UserDetailsService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value="userDetails")
public class UserDetailsResource {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostMapping(value="/save")
	public ResponseEntity<UserDetails> createUser(@RequestBody UserDetails userDetails){
		return new ResponseEntity<>(userDetailsService.createUserDetails(userDetails), HttpStatus.OK);
	}
	
	@PutMapping(value="/update")
	public ResponseEntity<UserDetails> updateUser(@RequestBody UserDetails userDetails){
		UserDetails updatedUserDetails = null;
		if(Objects.nonNull(userDetails.getId())){
			updatedUserDetails = userDetailsService.createUserDetails(userDetails);
		}
		return new ResponseEntity<>(updatedUserDetails, HttpStatus.OK);
	}
	
	@GetMapping(value="/getUser/{id}")
	public ResponseEntity<UserDetails> getUser(@PathVariable("id") int id) throws NotFoundException{
		return new ResponseEntity<>(userDetailsService.getUserById(id), HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllUsers")
	public ResponseEntity<Iterable<UserDetails>> getAllUsers(){
		return new ResponseEntity<>(userDetailsService.getAllUsers(), HttpStatus.OK);
	}
	
}
