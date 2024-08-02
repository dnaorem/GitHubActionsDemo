package com.kafka.connect.model;

import java.util.Objects;

public class TopicDetails {

	private String topicName;
	private String devStatus;
	private String sitStatus;
	private String uatStatus;
	private String prodStatus;
	private String userName;
	
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getDevStatus() {
		return devStatus;
	}
	public void setDevStatus(String devStatus) {
		this.devStatus = devStatus;
	}
	public String getSitStatus() {
		return sitStatus;
	}
	public void setSitStatus(String sitStatus) {
		this.sitStatus = sitStatus;
	}
	public String getUatStatus() {
		return uatStatus;
	}
	public void setUatStatus(String uatStatus) {
		this.uatStatus = uatStatus;
	}
	public String getProdStatus() {
		return prodStatus;
	}
	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public int hashCode() {
		return Objects.hash(devStatus, prodStatus, sitStatus, topicName, uatStatus, userName);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TopicDetails other = (TopicDetails) obj;
		return Objects.equals(devStatus, other.devStatus) && Objects.equals(prodStatus, other.prodStatus)
				&& Objects.equals(sitStatus, other.sitStatus) && Objects.equals(topicName, other.topicName)
				&& Objects.equals(uatStatus, other.uatStatus) && Objects.equals(userName, other.userName);
	}
	
	@Override
	public String toString() {
		return "TopicDetails [topicName=" + topicName + ", devStatus=" + devStatus + ", sitStatus=" + sitStatus
				+ ", uatStatus=" + uatStatus + ", prodStatus=" + prodStatus + ", userName=" + userName + "]";
	}
	
	
		
}
