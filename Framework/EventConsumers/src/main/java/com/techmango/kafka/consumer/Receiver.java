package com.techmango.kafka.consumer;

import java.util.LinkedHashMap;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.techmango.common.event.Event;

@Service
public class Receiver {

	@KafkaListener(topics = "${app.topic.topic}")
	public void listen(@Payload Event<?> event) {

		if (event.getNotificationType().equals("EMAIL")) {
			LinkedHashMap email = (LinkedHashMap) event.getNotificaionObj();
			System.out.println("enter in to Email listner" + email.get("ticketNumber")+" "+email.get("name"));

		}

		if (event.getNotificationType().equals("SMS")) {
			System.out.println("enter in to SMS listner");

		}

	}

}
