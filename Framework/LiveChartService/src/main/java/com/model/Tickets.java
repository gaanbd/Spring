package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "tickets")
public class Tickets {
 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
 
	@Column(name = "ticketstatus")
	private int ticketStatus;
 
	@Column(name = "ticketsubject")
	private String ticketSubject;
  
	public Tickets() {
	}
 
	public Tickets(String ticketsubject, int ticketstatus) {
		this.ticketSubject = ticketsubject;
		this.ticketStatus = ticketstatus;
	}
 
	public long getId() {
		return id;
	}

	public int getTicketstatus() {
		return ticketStatus;
	}

	public void setTicketstatus(int ticketstatus) {
		this.ticketStatus = ticketstatus;
	}

	public String getTicketsubject() {
		return ticketSubject;
	}

	public void setTicketsubject(String ticketsubject) {
		this.ticketSubject = ticketsubject;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Tickets [id=" + id + ", ticketstatus=" + ticketStatus + ", ticketsubject=" + ticketSubject + "]";
	}
 
	
	
}
