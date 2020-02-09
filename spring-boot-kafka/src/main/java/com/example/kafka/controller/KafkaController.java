package com.example.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.entity.User;
import com.example.kafka.service.ProducerService;

@RestController
@RequestMapping(value="/kafka")
public class KafkaController {

	private final ProducerService producer;

    @Autowired
    KafkaController(ProducerService producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        producer.sendMessage(message);
    }
    
    @PostMapping(value="/publishUser")
    public void sendMessageToKafkaForUser(@RequestBody User user) {
    	producer.sendMessageWithObject(user);
    }
}
