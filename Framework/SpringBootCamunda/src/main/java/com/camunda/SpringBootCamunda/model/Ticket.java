package com.camunda.SpringBootCamunda.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private String username;
	
	@Column
	private String customeroperatorname;
	
	@Column
	private String engineername;
	
	@Column
	private String servicecentername;

	@Column
	private String instanceId;

	@Column
	private boolean engineerWorkStatus;

	@Column
	private boolean productavailabality;

	@Column
	private boolean servicecenterstatus;

	@Column
	private boolean engAvailable; 
	
	@Column
	private boolean ticketDataValid;
	
	@Column
	private String ticketStatus; 
	
	@Column
	private String issue;
	
	@Column
	private String subject;
	
	@Column
	private String description;
	
	@Column
	private Date createdDate = new Date(System.currentTimeMillis());
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public boolean isEngineerWorkStatus() {
		return engineerWorkStatus;
	}

	public void setEngineerWorkStatus(boolean engineerWorkStatus) {
		this.engineerWorkStatus = engineerWorkStatus;
	}

	public boolean isProductavailabality() {
		return productavailabality;
	}

	public void setProductavailabality(boolean productavailabality) {
		this.productavailabality = productavailabality;
	}

	public boolean isServicecenterstatus() {
		return servicecenterstatus;
	}

	public void setServicecenterstatus(boolean servicecenterstatus) {
		this.servicecenterstatus = servicecenterstatus;
	}

	public boolean isEngAvailable() {
		return engAvailable;
	}

	public void setEngAvailable(boolean engAvailable) {
		this.engAvailable = engAvailable;
	}

	public boolean isTicketDataValid() {
		return ticketDataValid;
	}

	public void setTicketDataValid(boolean ticketDataValid) {
		this.ticketDataValid = ticketDataValid;
	}

	public String getCustomeroperatorname() {
		return customeroperatorname;
	}

	public void setCustomeroperatorname(String customeroperatorname) {
		this.customeroperatorname = customeroperatorname;
	}

	public String getEngineername() {
		return engineername;
	}

	public void setEngineername(String engineername) {
		this.engineername = engineername;
	}

	public String getServicecentername() {
		return servicecentername;
	}

	public void setServicecentername(String servicecentername) {
		this.servicecentername = servicecentername;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	
}
