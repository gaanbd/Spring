package com.techmango.springbootadminserver;

import org.springframework.stereotype.Component;

import com.techmango.common.mail.MailSender;
import com.techmango.common.mail.MailServers;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;

import de.codecentric.boot.admin.server.notify.Notifier;
import reactor.core.publisher.Mono;

@Component
public class Email implements Notifier {

	public void notify(ClientApplicationEvent arg0) {

		String status = arg0.getApplication().getStatus();
		if (status == "OFFLINE" || status == "DOWN") {
			String cc[] = {};
			String bcc[] = {};
			String to[] = { "harshanaa.r@techmango.net" };
			MailSender.sendTextMail("anandselva06@gmail.com", "selva1989", to, MailServers.GMAIL.getAddress(), "Failed Micro 						Service in TVSE", "Email Message:\n" + 
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

	@Override
	public Mono<Void> notify(InstanceEvent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
