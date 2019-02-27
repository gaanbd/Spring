package com.techmango.common.event;

import java.io.Serializable;
import java.util.Date;

public class Event<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Date createdOn = new Date(System.currentTimeMillis());

	private String notificationType;
	
	private T notificaionObj;

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public T getNotificaionObj() {
		return notificaionObj;
	}

	public void setNotificaionObj(T notificaionObj) {
		this.notificaionObj = notificaionObj;
	}

	

	
}
