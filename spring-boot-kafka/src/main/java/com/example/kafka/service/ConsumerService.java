package com.example.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

	private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
	
	@KafkaListener(topics = {"test","my-replicated-topic"},groupId = "myKafka")
	public void consume(String message){
		logger.info(String.format("#### -> Consumed message -> %s", message));
	}
}
