package com.camunda.SpringBootCamunda.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camunda.SpringBootCamunda.dao.TicketDAO;
import com.camunda.SpringBootCamunda.model.Ticket;
import com.camunda.SpringBootCamunda.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketDAO ticketDao;

	@Override
	public Ticket saveTicket(Ticket ticket) {
		return ticketDao.save(ticket);
	}

	@Override
	public List<Ticket> findByUsername(String username) {
		
		if (username.equals("prithvi")) { 
			return ticketDao.findByUsername(username);
		} else if (username.equals("sofia")) { 
			return ticketDao.findCustomeroperatorOpenTasks(username);
		} else if (username.equals("siva")) {
			return ticketDao.findEngineerOpenTasks(username);
		} else if (username.equals("jerald")) {
			return ticketDao.findServicecenterOpenTasks(username);
		}
		return null;
	}

	@Override
	public Ticket findById(long id) {
		return ticketDao.findById(id);
	}

	@Override
	public Ticket findByInstanceId(String instanceid)
	{
		return ticketDao.findByInstanceId(instanceid);
	}
}
