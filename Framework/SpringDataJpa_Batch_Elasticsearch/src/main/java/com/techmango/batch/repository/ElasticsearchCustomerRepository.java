package com.techmango.batch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import com.techmango.batch.domain.Customer;

@Repository
public interface ElasticsearchCustomerRepository extends ElasticsearchCrudRepository<Customer, Integer> {

}
