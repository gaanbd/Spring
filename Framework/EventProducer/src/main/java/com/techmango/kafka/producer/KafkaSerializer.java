package com.techmango.kafka.producer;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techmango.common.event.Event;

public class KafkaSerializer implements Serializer<Event> {

	
	
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		 // Do nothing because of X and Y.
	}

	@Override
	public void close() {
		 // Do nothing because of X and Y.
	}

	@Override
	public byte[] serialize(String topic, Event data) {
		byte[] retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(data).getBytes();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return retVal;
	}

}
