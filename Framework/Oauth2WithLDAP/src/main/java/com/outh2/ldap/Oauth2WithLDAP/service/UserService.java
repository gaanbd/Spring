package com.outh2.ldap.Oauth2WithLDAP.service;


import java.util.List;

import com.outh2.ldap.Oauth2WithLDAP.model.User;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(long id);
	public boolean verifyOTP(String otp, String username);
	public boolean sendActivationCode(String username);
	
}
