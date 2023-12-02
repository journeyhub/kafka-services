package com.userinfo.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userinfo.kafka.request.UserInfo;

@RestController
public class ProducerController {
	
	@Autowired
	KafkaTemplate<String, UserInfo> kafkaTemplate;
	
	private final String topic = "product-topic";
	
	/**
	 * Consumer to fetch messages from a Kafka topic named "product-topic" 
	 * and deserialize those messages into objects of the UserInfo class
	 * 
	 */
	
	@PostMapping("/api/kafka/publish")
	public void sendMessageToKafkaTopic(@RequestBody UserInfo message) {
		kafkaTemplate.send(topic, message);
	}
}
