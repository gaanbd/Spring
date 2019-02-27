package com.techmango.common.event;

import java.io.Serializable;

public class Email implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] cc = {};
	private String[] bcc = {};
	private String[] to = {};
	private String from;
	private String pwd;
	private String host;
	private String subject;
	private String content;
	private String defaultMailTo;

	
	public Email(String[] cc, String[] bcc, String[] to, String from, String pwd, String host, String subject,
			String content, String defaultMailTo) {
		super();
		this.cc = cc;
		this.bcc = bcc;
		this.to = to;
		this.from = from;
		this.pwd = pwd;
		this.host = host;
		this.subject = subject;
		this.content = content;
		this.defaultMailTo = defaultMailTo;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDefaultMailTo() {
		return defaultMailTo;
	}

	public void setDefaultMailTo(String defaultMailTo) {
		this.defaultMailTo = defaultMailTo;
	}

}
