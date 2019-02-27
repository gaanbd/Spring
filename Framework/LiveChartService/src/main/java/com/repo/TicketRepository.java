package com.repo;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.model.Tickets;

public interface TicketRepository extends CrudRepository<Tickets, Long> {
	
	
	@Query("select count(*) from Tickets t where t.ticketStatus = :status")
	int findByTicketStatus(int status);
	    
}