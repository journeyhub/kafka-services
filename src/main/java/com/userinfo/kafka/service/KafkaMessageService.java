package com.userinfo.kafka.service;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userinfo.kafka.request.UserInfo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Service
public class KafkaMessageService {
 
    @KafkaListener(topics = "product-topic", groupId = "test-group-id")
    public void listen(String message) {
        System.out.println("Received Message using @KafkaListener: " + message);
    }
    
    
    public List<UserInfo> consumeMessageFromKafkaTopic(){
    			// KafkaConsumer is a client that consumes records from a Kafka cluster. 
    			KafkaConsumer<String, String> consumer = null;
    			List<UserInfo> response = null;
    			try {
    			    // Initialize response as a new ArrayList to store the deserialized UserInfo objects
    			    response = new ArrayList<>();

    			    // Set up properties for the Kafka consumer
    			    Properties consumerProperties = new Properties();
    			    consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    			    consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    			    consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    			    consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
    			    consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    			    // Create a KafkaConsumer instance with the specified properties
    			    consumer = new KafkaConsumer<>(consumerProperties);

    			    // Subscribe the consumer to the "product-topic" Kafka topic
    			    consumer.subscribe(Arrays.asList("product-topic"));

    			    // Poll for records from Kafka for a duration of 10 seconds
    			    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));

    			    // Seek to the beginning of the topic to ensure we consume all available messages
    			    consumer.seekToBeginning(consumer.assignment());

    			    // Initialize an ObjectMapper for deserializing JSON
    			    ObjectMapper mapper = new ObjectMapper();

    			    // Process each record in the fetched records
    			    for (ConsumerRecord<String, String> record : records) {
    			        // Deserialize the JSON message into a UserInfo object
    			        UserInfo data = mapper.readValue(record.value(), UserInfo.class);

    			        // If deserialization is successful, add the UserInfo object to the response list
    			        if (data != null) {
    			            response.add(data);
    			        }

    			        // Print the raw message value to the console
    			        System.out.println("record " + record.value());
    			    }

    			    // Return the list of UserInfo objects as the response
    			    return response;

    			} catch (Exception e) {
    			    // Handle any exceptions that might occur during the process
    			    System.out.println("error " + e.getMessage());

    			} finally {
    			    // Ensure that the KafkaConsumer is closed in the finally block
    			    if (consumer != null) {
    			        consumer.close();
    			    }
    			}
				return response;
    }
}
