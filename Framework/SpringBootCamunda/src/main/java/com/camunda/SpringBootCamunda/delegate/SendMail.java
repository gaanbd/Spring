package com.camunda.SpringBootCamunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.techmango.common.mail.MailSender;
import com.techmango.common.mail.MailServers;

public class SendMail implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {
    	
    	String to[] = { "sofia@techmango.net" };
		String cc[] = {};
		String bcc[] = {};
    	MailSender.sendHtmlMail("sofia610.n@gmail.com", "sofiapencilfactory", to, 
				MailServers.GMAIL.getAddress(), "New Ticket",
				"new ticket is assigned for u.....", cc, bcc, "sofia610.n@gmail.com");
    }

}
