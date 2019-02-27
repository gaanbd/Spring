package com.techmango;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.techmango.common.event.Event;
import com.techmango.common.mail.Mail;
import com.techmango.kafka.producer.Sender;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Autowired
	private Sender sender;

	@Override
	public void run(String... args) throws Exception {
		Mail mail = new Mail("1213243", "ticket1");
		Event event = new Event();
		event.setCreatedOn(new Date());
		event.setNotificationType("EMAIL");
		event.setNotificaionObj(mail);
		sender.sendNotification(event);
	}

}
