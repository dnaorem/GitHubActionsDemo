package com.kafka.connect.model;

import java.util.Objects;

public class TopicDomain {
	
	private String domain;
    private String subdomain;
    private String environment;
    private String topicName;
	private String token;
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getSubdomain() {
		return subdomain;
	}
	public void setSubdomain(String subdomain) {
		this.subdomain = subdomain;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(domain, environment, subdomain, topicName, token);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TopicDomain other = (TopicDomain) obj;
		return Objects.equals(domain, other.domain) && Objects.equals(environment, other.environment)
				&& Objects.equals(subdomain, other.subdomain) && Objects.equals(topicName, other.topicName) && Objects.equals(token, other.token);
	}
	
	@Override
	public String toString() {
		return "UserDetails [domain=" + domain + ", subdomain=" + subdomain + ", environment=" + environment
				+ ", topicName=" + topicName + ", token=" + token + "]";
	}

    
}
