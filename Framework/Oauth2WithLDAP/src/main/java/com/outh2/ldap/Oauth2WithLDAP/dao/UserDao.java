package com.outh2.ldap.Oauth2WithLDAP.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.outh2.ldap.Oauth2WithLDAP.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    
	User findByUsername(String username); 
    
    
}
