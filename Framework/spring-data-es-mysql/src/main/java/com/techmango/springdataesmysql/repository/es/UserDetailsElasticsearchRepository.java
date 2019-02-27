package com.techmango.springdataesmysql.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.techmango.springdataesmysql.model.UserDetails;

@Repository
public interface UserDetailsElasticsearchRepository extends ElasticsearchRepository<UserDetails, Integer>{

}
