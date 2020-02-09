package com.example.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.example.kafka.entity.User;

@Service
public class ProducerService {
	
	private static final Logger logger =LoggerFactory.getLogger(ProducerService.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String,User> userKafkaTemplate;
	
	
	public void sendMessage(String message) {
		
		logger.info(String.format("#### -> Producing message -> %s", message));
		ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send("test", message);
		result.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.debug(String.format("Message is send successfully with offset is %d", result.getRecordMetadata().offset()));
				
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.debug(String.format("Error is occured when message is sending. An error is %s", ex.getMessage()));	
			}
			
		});
	}
	
	
	public void sendMessageWithObject(User user) {
		logger.info(String.format("#### -> Producing object message -> %s", user.toString()));
		userKafkaTemplate.send("my-replicated-topic", user);
	}
	
}
