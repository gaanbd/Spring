package com.techmango.kafka.failedevents;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsDAO extends CrudRepository<EventFailure, Long>{
	
}
