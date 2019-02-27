package com.techmango.kafka.consumer;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmango.common.event.Event;


@SuppressWarnings("rawtypes")
public class KafkaDeserializer implements Deserializer<Event> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	@Override
	public Event deserialize(String topic, byte[] objectData) {
	    ObjectMapper mapper = new ObjectMapper();
	    Event event = null;
	    try {
	    	event = mapper.readValue(objectData, Event.class);
	    } catch (Exception e) {
	 
	      e.printStackTrace();
	    }
	    return event;
	}

	@Override
	public void close() {
	}

}
