package com.outh2.mysql.Oauth2WithMySQL.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.outh2.mysql.Oauth2WithMySQL.model.User;
import com.outh2.mysql.Oauth2WithMySQL.service.UserService;
import com.techmango.common.response.Response;


@RestController
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	public ResponseEntity<Response> create(@RequestBody User user) {

		User userObj = userService.save(user);
		Response response; 
		if (userObj == null) {
			response = new Response("false","User Already Exist");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			response = new Response("true","Registered Successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

	}

	// Registration//

	@RequestMapping(value = "/verifyotp", method = RequestMethod.POST)
	public ResponseEntity<Response> verifyOTP(@RequestParam(value = "otp") String otp,
			@RequestParam(value = "username") String username) {

		Response response; 
		boolean status = userService.verifyOTP(otp, username, true);
		if (status) {
			response = new Response("true","OTP Verified Successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response = new Response("false","OTP Not Correct");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/resendotp/{username}", method = RequestMethod.GET)
	public ResponseEntity<Response> resendOTP(@PathVariable(value = "username") String username) {

		Response response; 
		boolean status = userService.sendActivationCode(username, true);
		if (status) {
			response = new Response("true","OTP Sent Successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response = new Response("false","OTP Not Sent");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Forgot Password//

	@RequestMapping(value = "/forgotpasswordsendotp/{username}", method = RequestMethod.GET)
	public ResponseEntity<Response> forgotPasswordSendOTP(@PathVariable(value = "username") String username) {

		Response response; 
		boolean status = userService.sendActivationCode(username, false);
		if (status) {
			response = new Response("true","OTP Sent Successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response = new Response("false","OTP Not Sent");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/forgotpasswordverifyotp", method = RequestMethod.POST)
	public ResponseEntity<Response> forgotPasswordVerifyOTP(@RequestParam(value = "otp") String otp,
			@RequestParam(value = "username") String username) {

		Response response; 
		boolean status = userService.verifyOTP(otp, username, false);
		if (status) {
			response = new Response("true","OTP Verified Successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response = new Response("false","OTP Not Correct");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public ResponseEntity<Response> changePassword(@RequestParam(value = "password") String password,
			@RequestParam(value = "username") String username) {
		boolean status = userService.updatepassword(username, password);
		Response response; 
		if (status) {
			response = new Response("true","Password Changed Successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response = new Response("false","Change Password Failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
