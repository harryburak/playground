package com.example.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.example.kafka.entity.User;
import com.example.kafka.request.FootballRequest;

@Configuration
public class KafkaProducer {

	@Value(value= "${kafka.producer.bootstrap-servers}")
	private String bootStrapAddress;
	
	@Bean
	public ProducerFactory<String, String> producerFactory(){
		Map<String,Object> config = new HashMap<>();
		defaultConfig(config);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
		return new DefaultKafkaProducerFactory<>(config);
	}
	
	@Bean
	public ProducerFactory<String, User> userProducerFactory(){
		return new DefaultKafkaProducerFactory<>(defaultObjectConfig());
	}
	
	@Bean
	public ProducerFactory<String,FootballRequest> footballProducerFactory(){
		Map<String,Object> config = defaultObjectConfig();
		config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
		config.put(ProducerConfig.RETRIES_CONFIG, 3);
		config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15_000);
		config.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1_000);
		return new DefaultKafkaProducerFactory<>(defaultObjectConfig());
	}
	
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}
	
	@Bean
	public KafkaTemplate<String, User> userKafkaTemplate(){
		return new KafkaTemplate<>(userProducerFactory());
	}
	
	@Bean
	public KafkaTemplate<String, FootballRequest> footballKafkaTemplate(){
		return new KafkaTemplate<>(footballProducerFactory());
	}
	
	private void defaultConfig(Map<String,Object> config) {
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddress);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	}
	
	private Map<String,Object> defaultObjectConfig() {
		Map<String,Object> config = new HashMap<>();
		defaultConfig(config);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,JsonSerializer.class);
		return config;
	}
}
