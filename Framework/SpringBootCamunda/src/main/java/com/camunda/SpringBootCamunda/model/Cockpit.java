package com.camunda.SpringBootCamunda.model;

import java.util.Date;

public class Cockpit {

	private boolean isrun;
	private String ProcessInstance;	
	private Date startDate;
	private Date endDate;
	private String assignee;
	private String deleteReason;
	
	public Cockpit()
	{}
	
	public boolean isIsrun() {
		return isrun;
	}

	public void setIsrun(boolean isrun) {
		this.isrun = isrun;
	}

	public String getProcessInstance() {
		return ProcessInstance;
	}

	public void setProcessInstance(String processInstance) {
		ProcessInstance = processInstance;
	}

	public Cockpit(boolean isrun, String processInstance) {
		super();
		this.isrun = isrun;
		ProcessInstance = processInstance;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	
}
