package com.kafka.connect.model;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import com.kafka.connect.GitPushExample;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component

public class AdminConfigurer {

	@Autowired
	private KafkaConfig kafkaConfig;
	AdminClient adminClient;
	
	@Autowired
	GitPushExample gitPushExample;
	
	@Value(value = "${review.status}")
	String reviewStatus;

	public void getServer() {
		System.out.println("========== " + kafkaConfig.getBootstrapServers());
	}

	@Bean
	public Map<String, Object> kafkaAdminProperties() {
		final Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
	//	if (kafkaConfig.gets().getEnabled() && kafkaConfig.getSsl().getEnabled()) {
			configs.put("sasl.mechanism", kafkaConfig.getSaslMechanism());
			configs.put("security.protocol", kafkaConfig.getSecurityProtocol());
		//	configs.put("ssl.keystore.location", kafkaConfig.getSsl().getKeystoreLocation());
		//	configs.put("ssl.keystore.password", kafkaConfig.getSsl().getKeystorePassword());
		//	configs.put("ssl.truststore.location", kafkaConfig.getSsl().getTruststoreLocation());
		//	configs.put("ssl.truststore.password", kafkaConfig.getSsl().getTruststorePassword());
			/*configs.put("sasl.jaas.config",
					String.format(kafkaConfig.getJaasTemplate(),
							kafkaConfig.getProperties().getSasl().getJaas().getConfig().getUsername(),
							kafkaConfig.getProperties().getSasl().getJaas().getConfig().getPassword()));
*/			
			configs.put("sasl.jaas.config",kafkaConfig.getSsaslJaasConfig());
			configs.put("client.dns.lookup", kafkaConfig.getClientDnsLookup());
			configs.put("session.timeout.ms", kafkaConfig.getSessionTimeout());
			configs.put("acks", kafkaConfig.getAcks());
	//	}
		
			// ############################# TO BE CHANGED, Just for Test
			adminClient = AdminClient.create(configs);
			ListTopicsResult topics = adminClient.listTopics();
			System.out.println("#################### reviewStatus: " + reviewStatus);
			try {
				topics.names().get().forEach(System.out::println);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	//		gitPushExample.commitGit();
			// ###############################################
		return configs;
	}

	/*
	 * @Bean public AdminClient getClient() { return
	 * AdminClient.create(kafkaAdminProperties()); }
	 */

}
