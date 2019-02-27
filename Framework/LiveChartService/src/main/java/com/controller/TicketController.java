package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.Tickets;
import com.repo.TicketRepository;


@CrossOrigin(origins = "http://localhost:4201")
@RestController
@RequestMapping(value = "/tickets")
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	ApplicationContext applicationContext;
	
	@GetMapping(value = "/create", produces = "application/json")
	public Tickets createTicket(@RequestParam("ticketStatus") int ticketStatus, 
			@RequestParam("ticketSubject") String ticketSubject) {
		Tickets ticket = ticketRepository.save(new Tickets(ticketSubject, ticketStatus));
		return ticket;
	}
	
	
	
}
