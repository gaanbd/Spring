package com.outh2.mysql.Oauth2WithMySQL.service;



import java.util.List;

import com.outh2.mysql.Oauth2WithMySQL.model.User;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(long id);
	public boolean verifyOTP(String otp, String username, boolean isFromRegisteration);
	public boolean sendActivationCode(String username, boolean isFromRegistration);
	public boolean updatepassword(String username, String password);
	
}
