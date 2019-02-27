package com.camunda.SpringBootCamunda.service;

import java.util.List;

import com.camunda.SpringBootCamunda.model.Ticket;

public interface TicketService {

	Ticket saveTicket(Ticket ticket);
	
	List<Ticket> findByUsername(String username);
	
	Ticket findById(long id);
	
	Ticket findByInstanceId(String instanceid);
}
