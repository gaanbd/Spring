package com.techmango.springdataesmysql.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.techmango.springdataesmysql.model.UserDetails;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetails, Integer>{

}
