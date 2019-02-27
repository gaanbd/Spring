package com.outh2.ldap.Oauth2WithLDAP.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.outh2.ldap.Oauth2WithLDAP.model.User;
import com.outh2.ldap.Oauth2WithLDAP.service.UserService;



@RestController
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	public ResponseEntity<Object> create(@RequestBody User user) {

		Map<String, String> response = new HashMap<String, String>();		
		User userObj = userService.save(user);
		if (userObj == null) {
			response.put("error", "User Already Exist");
			return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			response.put("success", "Registered Successfully");
			return new ResponseEntity<>(response,HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/verifyotp/{otp}/{username}", method = RequestMethod.GET)
	public boolean verifyOTP(@PathVariable(value = "otp") String otp,
			@PathVariable(value = "username") String username) {
		return userService.verifyOTP(otp, username);
	}

	@RequestMapping(value = "/resendotp/{username}", method = RequestMethod.GET)
	public boolean verifyOTP(@PathVariable(value = "username") String username) {
		return userService.sendActivationCode(username);
	}

}
