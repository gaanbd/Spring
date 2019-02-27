package com.outh2.mysql.Oauth2WithMySQL.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.outh2.mysql.Oauth2WithMySQL.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    
	User findByUsername(String username); 
    
    User findByEmail(String email);
}
