package com.techmango.common.audit;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class AuditLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -715294950236661974L;

	private UUID requestRefId;
	
	private String createdBy;
	
	private Date createdDate;
	
	private String updatedBy;
	
	private Date updatedDate;
	
	private String requestObject;
	
	private String operation;
	
	private String serviceName;
	
	private String msg;
	
	public UUID getRequestRefId() {
		return requestRefId;
	}

	public void setRequestRefId(UUID requestRefId) {
		this.requestRefId = requestRefId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public String getRequestObject() {
		return requestObject;
	}

	public void setRequestObject(String requestObject) {
		this.requestObject = requestObject;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
