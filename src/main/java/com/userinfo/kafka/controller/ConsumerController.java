package com.userinfo.kafka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userinfo.kafka.request.UserInfo;
import com.userinfo.kafka.service.KafkaMessageService;

@RestController
public class ConsumerController {

	@Autowired
	KafkaMessageService kafkaService;

	@GetMapping("/api/messages")
	public ResponseEntity<List<UserInfo>> consumeMessagesKafka() {
		List<UserInfo> userInfomation = kafkaService.consumeMessageFromKafkaTopic();
		return new ResponseEntity<>(userInfomation, HttpStatus.OK);
	}

}
