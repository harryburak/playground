package com.example.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.kafka.entity.User;

@EnableKafka
@Configuration
public class KafkaConsumer {

	@Value(value="${kafka.producer.bootstrap-servers}")
	private String bootstrapAddress;
	
	@Value(value="${kafka.consumer.auto-offset-reset}")
	private String offsetReset;
	
	@Value(value="${kafka.consumer.group-id}")
	private String groupId;
	
	@Value(value="${kafka.consumer.user-group-id}")
	private String userGroupId;
	
	
	
	private void defaultConsumerConfig(Map<String,Object> config,String groupId) {
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);

	}
	
	public ConsumerFactory<String,User> userConsumerFactory(String groupId){
		Map<String,Object> config = new HashMap<>();
		defaultConsumerConfig(config, groupId);
		return new DefaultKafkaConsumerFactory<>(config,null,new JsonDeserializer<>(User.class));
	}
	
	
	public ConsumerFactory<String, String> consumerFactory(String groupId){
		Map<String,Object> config = new HashMap<>();
		defaultConsumerConfig(config, groupId);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config);
	}
	
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(groupId));
        return factory;
    }
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> kafkaUserListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userConsumerFactory(userGroupId));
        return factory;
    }
	
	
}
