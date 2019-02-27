package com.camunda.SpringBootCamunda.controller;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.SpringBootCamunda.model.Ticket;
import com.camunda.SpringBootCamunda.service.CamundaService;
import com.camunda.SpringBootCamunda.service.TicketService;
import com.techmango.common.mail.MailSender;
import com.techmango.common.mail.MailServers;
import com.techmango.common.response.Response;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private CamundaService camundaService;

	@Autowired
	private RuntimeService runtimeService;

	@RequestMapping(value = "/completetask", method = RequestMethod.POST)
	public List<Ticket> completeTask(@RequestParam String instanceId, @RequestParam String username) {
		String taskId = camundaService.getTaskId(instanceId, username);
		camundaService.completeTask(taskId);
		if (camundaService.getAssigneeTaskByProcessInstanceId(instanceId) == 0) {
			Ticket ticket = ticketService.findByInstanceId(instanceId);
			if (ticket.getCustomeroperatorname().equals(username)) {
				ticket.setTicketStatus("closed");
				ticketService.saveTicket(ticket);
			}
		}

		return ticketService.findByUsername(username);
	}

	@RequestMapping(value = "/assignengineer", method = RequestMethod.POST)
	public ResponseEntity<Response> assignEngineer(@RequestParam String engineer, @RequestParam long ticketId) {
		camundaService.assignEngineer(engineer, ticketId);
		Response response = new Response("true", "Assigned");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/assignserviceengineer", method = RequestMethod.POST)
	public ResponseEntity<Response> assignServiceEngineer(@RequestParam String serviceengineer,
			@RequestParam long ticketId) {
		camundaService.assignServiceEngineer(serviceengineer, ticketId);
		Response response = new Response("true", "Assigned");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getservicecenterstatus", method = RequestMethod.POST)
	public ResponseEntity<Response> getServiceCenterStatus(@RequestParam boolean status, @RequestParam long ticketId) {
		camundaService.getServiceCenterStatus(status, ticketId);
		Response response = new Response("true", "Status Recevied");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/createticket", method = RequestMethod.POST)
	public List<Ticket> create(@RequestBody Ticket ticket, @RequestParam String username, @RequestParam String assignee)
			throws FileNotFoundException {
		
		String executionId = camundaService.startProcess(assignee);
		ticket.setCustomeroperatorname(assignee);
		ticket.setInstanceId(executionId);
		ticket.setEngAvailable((boolean) runtimeService.getVariable(executionId, "available"));
		ticket.setProductavailabality((boolean) runtimeService.getVariable(executionId, "productavailable"));
		ticket.setTicketDataValid((boolean) runtimeService.getVariable(executionId, "valid"));
		ticket.setTicketStatus("New");
		ticket.setUsername(username);
		Ticket ticketObj = ticketService.saveTicket(ticket);
		return ticketService.findByUsername(username);
	}

	@RequestMapping(value = "/getallusertickets", method = RequestMethod.POST)
	public List<Ticket> listUserTickets(@RequestParam String username) {
		return ticketService.findByUsername(username);
	}

	@RequestMapping(value = "/getticketbyid", method = RequestMethod.POST)
	public Ticket getTicketById(@RequestParam long ticketid) {
		return ticketService.findById(ticketid);
	}

	/*
	 * @RequestMapping(value = "/starttestprocess", method = RequestMethod.POST)
	 * public List<Ticket> startTestProcess(@RequestParam String
	 * username, @RequestParam String assignee) throws FileNotFoundException {
	 * 
	 * Ticket ticket = new Ticket(); String executionId =
	 * camundaService.startProcess(assignee);
	 * runtimeService.getVariable(executionId, "valid");
	 * runtimeService.getVariable(executionId, "available");
	 * runtimeService.getVariable(executionId, "productavailable");
	 * ticket.setUsername(username); ticket.setInstanceId(executionId);
	 * ticket.setEngAvailable((boolean) runtimeService.getVariable(executionId,
	 * "available")); ticket.setProductavailabality((boolean)
	 * runtimeService.getVariable(executionId, "productavailable"));
	 * ticket.setTicketDataValid((boolean)
	 * runtimeService.getVariable(executionId, "valid")); Ticket ticketObj =
	 * ticketService.saveTicket(ticket); return
	 * ticketService.findByUsername(username);
	 * 
	 * }
	 */
	
}
