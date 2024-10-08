package com.kafka.connect;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class ConnectKafkaApplication {

	public static void main(String[] args) {	
		SpringApplication.run(ConnectKafkaApplication.class, args);

	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}	
	
	@Bean(name = "cloneRepo", initMethod = "fetchGitFile")
    public CloneRepo getCloneRepo(){
        return new CloneRepo();
    }

}
