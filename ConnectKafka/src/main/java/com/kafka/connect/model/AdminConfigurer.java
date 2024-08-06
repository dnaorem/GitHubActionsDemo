package com.kafka.connect.model;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import com.kafka.connect.GitPushAndPullRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Component

public class AdminConfigurer {

	@Autowired
	private KafkaConfig kafkaConfig;
	AdminClient adminClient;
	

	@Value(value = "${review.status}")
	String reviewStatus;

	public void getServer() {
		System.out.println("========== " + kafkaConfig.getBootstrapServers());
	}

	@Bean
	public Map<String, Object> kafkaAdminProperties() {
		final Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
			configs.put("sasl.mechanism", kafkaConfig.getSaslMechanism());
			configs.put("security.protocol", kafkaConfig.getSecurityProtocol());
			configs.put("sasl.jaas.config",kafkaConfig.getSsaslJaasConfig());
			configs.put("client.dns.lookup", kafkaConfig.getClientDnsLookup());
			configs.put("session.timeout.ms", kafkaConfig.getSessionTimeout());
			configs.put("acks", kafkaConfig.getAcks());

			if(reviewStatus.equals("Approved")) {
				createTopic(configs);
			}

		return configs;
	}
	
	public void createTopic(Map<String, Object> configs) {
		adminClient = AdminClient.create(configs);
		try {
			Set<String> topics = adminClient.listTopics().names().get();
		    if(topics.contains(kafkaConfig.getKafkaTopicName())) {
		    	System.out.println("Topic '" + kafkaConfig.getKafkaTopicName() + "' already created!");
		    } else {
	
			    NewTopic newTopic = new NewTopic(kafkaConfig.getKafkaTopicName(), kafkaConfig.getKafkaTopicPartitions(), kafkaConfig.getKafkaTopicReplicationFactor());
			    adminClient
				  .createTopics(Collections.singleton(newTopic))
				  .values().get(kafkaConfig.getKafkaTopicName()) .get();
		   
			    System.out.println("#################### Topic: " + kafkaConfig.getKafkaTopicName() + " created successfully!");
		//	    topics = adminClient.listTopics().names().get();
		//		topics.forEach(System.out::println);
				
			}
	    } catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public boolean topicAlreadyExists() {
		try {
			Set<String> topics = adminClient.listTopics().names().get();
		    if(topics.contains(kafkaConfig.getKafkaTopicName())) {
		    	return true;
		    }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * @Bean public AdminClient getClient() { return
	 * AdminClient.create(kafkaAdminProperties()); }
	 */
	 

}
