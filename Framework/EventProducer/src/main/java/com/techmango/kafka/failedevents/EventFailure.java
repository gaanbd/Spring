package com.techmango.kafka.failedevents;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EventFailure {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private byte[] eventObj;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getEventObj() {
		return eventObj;
	}

	public void setEventObj(byte[] eventObj) {
		this.eventObj = eventObj;
	}
	
	
}
