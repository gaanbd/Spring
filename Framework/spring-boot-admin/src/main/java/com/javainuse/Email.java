package com.javainuse;

import org.springframework.stereotype.Component;

import com.techmango.common.mail.MailSender;
import com.techmango.common.mail.MailServers;

import de.codecentric.boot.admin.event.ClientApplicationEvent;
import de.codecentric.boot.admin.notify.Notifier;
@Component
public class Email implements Notifier {

	@Override
	public void notify(ClientApplicationEvent arg0) {
		
		String status=arg0.getApplication().getStatusInfo().getStatus();
		//String service=arg0.getApplication().getName();
		if (status== "OFFLINE" || status== "DOWN") {
			String cc[]= {};
			String bcc[]= {};
			String to[]= {"selvaananth123@gmail.com"};
			//System.out.println("inside Notify method"+arg0.getApplication().getStatusInfo().getStatus());
			//System.out.println();
			MailSender.sendTextMail("anandselva06@gmail.com", "selva1989", to, MailServers.GMAIL.getAddress(), "Failed Micro Service in TVSE", "Email Message:\n" + 
					"Failed service:\n" + 
					"Down application service,\n" + 
					"Spring boot:\n" + 
					"The first down the service and .\n" + 
					"\n" + 
					"Email Signature\n" + 
					"FirstName LastName\n" + 
					"Email address\n" + 
					"Phone\n" + 
					"ThechMango profile (optional)", cc, bcc, "selvaananth123@gmail.com");
		}
		
	}

}
