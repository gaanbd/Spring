package com.techmango.kafka.producer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.techmango.common.event.Event;
import com.techmango.kafka.failedevents.EventFailure;
import com.techmango.kafka.failedevents.EventsDAO;

import antlr.collections.List;

@Service
public class Sender {

	@Autowired
	private KafkaTemplate<String, Event> kafkaTemplate;

	@Value("${app.topic.topic}")
	private String topic;

	@Value("${spring.kafka.bootstrap-servers}")
	private String serverDomain;

	@Autowired
	private EventsDAO eventsDAO;

	public void sendNotification(Event event) {

		try {

			System.out.println("comming inside.........");
			String serverAddress[] = serverDomain.split(":");
			Socket s = new Socket(serverAddress[0], Integer.parseInt(serverAddress[1]));

			// getting the failed events from db
			Iterable<EventFailure> failedEvents = eventsDAO.findAll();
			int size=0;
			if (failedEvents instanceof Collection) {
			size= ((Collection<?>) failedEvents).size();
			}
			if (failedEvents != null && size>0) {
				System.out.println("enter into failure handle mode..");
				for (EventFailure eventFailure : failedEvents) {
					byte[] eventObj = eventFailure.getEventObj();
					try {
						ByteArrayInputStream bis = new ByteArrayInputStream(eventObj);
						ObjectInput in1;
						in1 = new ObjectInputStream(bis);
						Object o = in1.readObject();
						if (o != null) {
							sendMessage(((Event) o));
							eventsDAO.deleteById(eventFailure.getId());
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			}
			sendMessage(event);

		} catch (Exception e) {
			System.out.println("enter into not connected mode============> server down");

			// save it in to database
			EventFailure failedEvent = new EventFailure();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream objOstream;
			try {
				objOstream = new ObjectOutputStream(baos);
				objOstream.writeObject(event);
				objOstream.flush();
				objOstream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			byte[] bArray = baos.toByteArray();
			failedEvent.setEventObj(bArray);
			eventsDAO.save(failedEvent);

		}

	}

	public void sendMessage(Event event) {

		ListenableFuture<SendResult<String, Event>> future = kafkaTemplate.send(topic, event);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Event>>() {

			@Override
			public void onSuccess(SendResult<String, Event> result) {
				System.out.println(
						"sent message='{}' with offset={}" + event + "," + result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("unable to send message='{}'" + event + "," + ex);
			}
		});
	}
}
