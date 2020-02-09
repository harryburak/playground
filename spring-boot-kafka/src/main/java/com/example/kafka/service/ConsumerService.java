package com.example.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.kafka.entity.User;

@Service
public class ConsumerService {

	private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
	
	@KafkaListener(topics = {"test","my-replicated-topic"},groupId = "myKafka",containerFactory = "kafkaListenerContainerFactory")
	public void consumeString(String message){
		logger.info(String.format("#### -> Consumed message -> %s", message));
	}
	
	@KafkaListener(topics = {"my-replicated-topic"},groupId = "userKafka",containerFactory = "kafkaUserListenerContainerFactory")
	public void consumeUser(User user){
		logger.info(String.format("#### -> Consumed message -> %s", user));
	}
	
}
