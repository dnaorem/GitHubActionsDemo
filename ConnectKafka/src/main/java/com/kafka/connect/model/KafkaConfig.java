package com.kafka.connect.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
	
	@Value(value = "${bootstrap.servers}")
	private String bootstrapServers;
	
	@Value(value = "${security.protocol}")
	private String securityProtocol;
	
	@Value(value = "${sasl.jaas.config}")
	private String ssaslJaasConfig;
	
	@Value(value = "${sasl.mechanism}")
	private String saslMechanism;
	
	@Value(value = "${client.dns.lookup}")
	private String clientDnsLookup;
	
	@Value(value = "${session.timeout.ms}")
	private String sessionTimeout;

	@Value(value = "${acks}")
	private String acks;

	/*
	 * @Value(value = "${client.name.server}") private String clientServer;
	 */
	
	public String getBootstrapServers() {
		return bootstrapServers;
	}

	public void setBootstrapServers(String bootstrapServers) {
		this.bootstrapServers = bootstrapServers;
	}

	public String getSecurityProtocol() {
		return securityProtocol;
	}

	public void setSecurityProtocol(String securityProtocol) {
		this.securityProtocol = securityProtocol;
	}

	public String getSsaslJaasConfig() {
		return ssaslJaasConfig;
	}

	public void setSsaslJaasConfig(String ssaslJaasConfig) {
		this.ssaslJaasConfig = ssaslJaasConfig;
	}

	public String getSaslMechanism() {
		return saslMechanism;
	}

	public void setSaslMechanism(String saslMechanism) {
		this.saslMechanism = saslMechanism;
	}

	public String getClientDnsLookup() {
		return clientDnsLookup;
	}

	public void setClientDnsLookup(String clientDnsLookup) {
		this.clientDnsLookup = clientDnsLookup;
	}

	public String getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(String sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public String getAcks() {
		return acks;
	}

	public void setAcks(String acks) {
		this.acks = acks;
	}

	/*
	 * public String getClientServer() { return clientServer; }
	 * 
	 * public void setClientServer(String clientServer) { this.clientServer =
	 * clientServer; }
	 */


}
