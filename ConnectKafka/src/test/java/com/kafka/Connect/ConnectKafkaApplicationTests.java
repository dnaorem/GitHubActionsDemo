package com.kafka.Connect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ConnectKafkaApplicationTests {
	/*
	 * @Autowired RestTemplate restTemplate;
	 * 
	 * @Test void contextLoads() {
	 * System.out.println("================== Testing started!");
	 * ResponseEntity<String> response =
	 * restTemplate.getForEntity("http://192.168.0.101:8080/create?name=DJ",
	 * String.class); System.out.println("================== Response Code:"
	 * +response.getStatusCode()); assertEquals(HttpStatus.OK,
	 * response.getStatusCode());
	 * 
	 * }
	 */

}
