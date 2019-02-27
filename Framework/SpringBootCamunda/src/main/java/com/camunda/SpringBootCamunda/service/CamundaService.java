package com.camunda.SpringBootCamunda.service;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camunda.SpringBootCamunda.model.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class CamundaService {
	
	public static String instanceId = "";

	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TicketService ticketService;

	public String startProcess(String assignee) throws FileNotFoundException {

		/*HashMap<String,Object> result =
				  new ObjectMapper().readValue(JSON_SOURCE, HashMap.class);*/
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("customerServiceOperator", assignee);
		variables.put("valid", true);
		variables.put("available", true);
		variables.put("initiator", assignee);
		variables.put("productavailable", true); 
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("TicketCreation", variables);
		instanceId = instance.getProcessInstanceId();		
		return instanceId;
	}
	
	public List<Task> getTasks(String assignee) {
		return taskService.createTaskQuery().taskAssignee(assignee).list();
	}

	public void completeTask(String taskId) {
		taskService.complete(taskId);	
	}

	public void assignEngineer(String engineername, long ticketId) {
		Ticket ticket = ticketService.findById(ticketId);
		ticket.setEngineername(engineername);
		ticket.setTicketStatus("InProgress");
		Ticket ticketObj = ticketService.saveTicket(ticket);
		runtimeService.setVariable(instanceId, "engineer", engineername);
	}
	
	public void setEngineerStatus(boolean status, long ticketId) {		
		System.out.println(instanceId);		
		Ticket ticket = ticketService.findById(ticketId);
		ticket.setEngineerWorkStatus(status);
		Ticket ticketObj = ticketService.saveTicket(ticket);		
	}
	
	public void assignServiceEngineer(String serviceengineer, long ticketId) {		
		System.out.println(instanceId);
		Ticket ticket = ticketService.findById(ticketId);
		ticket.setServicecentername(serviceengineer);
		Ticket ticketObj = ticketService.saveTicket(ticket);
		runtimeService.setVariable(instanceId, "serviceengineer", serviceengineer);
		setEngineerStatus(true, ticketId);
		System.out.println("serviceengineer "+serviceengineer);
	}
	
	public void getServiceCenterStatus(boolean status, long ticketId) {		
		System.out.println(instanceId);		
		Ticket ticket = ticketService.findById(ticketId);
		ticket.setServicecenterstatus(status);
		Ticket ticketObj = ticketService.saveTicket(ticket);
	}
	
	public String getTaskId(String processInstanceId, String assignee) {
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).orderByTaskCreateTime().desc().processInstanceId(processInstanceId).list();
		return tasks.get(0).getId();
	}
	
	public int getAssigneeTaskByProcessInstanceId(String processInstanceId) {
		List<Task> tasks = taskService.createTaskQuery().orderByTaskCreateTime().desc().processInstanceId(processInstanceId).list();
		return tasks.size();
	}
	
	
	

}
