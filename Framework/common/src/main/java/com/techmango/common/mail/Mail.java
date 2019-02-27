package com.techmango.common.mail;

import java.io.Serializable;

public class Mail  implements Serializable{
	

	private static final long serialVersionUID = 1L;

	private String ticketNumber;
	
	private String name;

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Mail(String ticketNumber, String name) {
		this.ticketNumber = ticketNumber;
		this.name = name;
	}
	
	

}
