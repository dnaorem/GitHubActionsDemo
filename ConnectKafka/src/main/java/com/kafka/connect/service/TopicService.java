package com.kafka.connect.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.kafka.connect.GitPushExample;
import com.kafka.connect.model.TopicDetails;	

@Service
public class TopicService {
	
	//@Autowired
	//AdminClient adminClient;
	
	@Autowired
	GitPushExample gitPushExample;

	public List<TopicDetails> getAllTopics() {
		System.out.println("################## Getting Topics");
		Connection conn = DBConnection.getConnection();
		TopicDetails topicDetails;
		List<TopicDetails> topics = new ArrayList<>();
		try {

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from topics");
			while (rs.next()) {
				topicDetails = new TopicDetails();
				topicDetails.setTopicName(rs.getString(2));
				topicDetails.setDevStatus(rs.getString(3));
				topicDetails.setSitStatus(rs.getString(5));
				topicDetails.setUatStatus(rs.getString(6));
				topicDetails.setProdStatus(rs.getString(7));
				topicDetails.setUserName(rs.getString(4));
				topics.add(topicDetails);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return topics;

	}
	
	public String createTopic(TopicDetails topicDetails) throws ExecutionException, InterruptedException{
		int partitions = 1;
	    short replicationFactor = 3;
	    
	/*    Set<String> topics = adminClient.listTopics().names().get();
	    if(topics.contains(topicDetails.getTopicName())) {
	    	System.out.println("Topic already created!");
	    	return "Topic already created!";
	    }
	    
	    NewTopic newTopic = new NewTopic(topicDetails.getTopicName(), partitions, replicationFactor);
	    adminClient
		  .createTopics(Collections.singleton(newTopic))
		  .values().get(topicDetails.getTopicName()) .get();
	  */  
	    if(updateTopicStatus(topicDetails).equals("passed"))
	    	return "Topic Created Successfully in Confluent Kafka!";
	    else 
	    	return "Topic could not be created in Confluent Kafka!";
	}

	public String addTopic(String topicName, String userName, String token) throws InterruptedException, ExecutionException {
		
		if(topicAlreadyAdded(topicName)) {
			return "Topic Already Added!";
		}
		Connection conn = DBConnection.getConnection();
		try {

			Statement stmt = conn.createStatement();
			//boolean result = stmt.execute("INSERT INTO Topics(Name,DEV_Status,SIT_Status,UAT_Status,PROD_Status,UserName) VALUES ('"+topicName+"', 'Pending','Pending','Pending','Pending', '"+userName+"');");
	
	/*		File propertiesDir = new File("src\\main\\resources");
			File fileInDevelop = Arrays.stream(propertiesDir.listFiles())
					.filter(f -> f.getName().contains("application.properties"))
					.findFirst()
					.get();
			Files.asCharSink(fileInDevelop, Charset.defaultCharset(), FileWriteMode.APPEND).write("\n\nkafka.topic.name=" + topicName);
	*/
			
			File f1 = new File("src\\main\\resources\\application.properties");
		    FileInputStream in = new FileInputStream(f1);
		    Properties config = new Properties();
		    config.load(in);

		    config.setProperty("kafka.topic.name",topicName);

		    // get or create the file
		    File f2 = new File("src\\main\\resources\\application.properties");
		    OutputStream out = new FileOutputStream(f2);
		    config.store(out, "App properties file comment");
			
			
			gitPushExample.commitGit(token);
		} catch (Exception e){
			System.out.println(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Topic added Successfully!";
	}
	
	public boolean topicExists(String topicName) throws InterruptedException, ExecutionException {
		Set<String> topics =  null;//adminClient.listTopics().names().get();
	    if(topics.contains(topicName)) {
	    	System.out.println("Topic already created!");
	    	return true;
	    }
	    return false;
	}
	
	public boolean topicAlreadyAdded(String topicName) {
		Connection conn = DBConnection.getConnection();
		try {

			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("Select * from Topics Where Name='"+topicName+"'");
			if(result.next()) {
				return true;
			}
		} catch (Exception e){
			System.out.println(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public String updateTopicStatus(TopicDetails topicDetails) {
		
		Connection conn = DBConnection.getConnection();
		String resultMsg = "passed";
		try {
			String envToBeApproved = "";
			if(topicDetails.getDevStatus().equals("Pending")) {
				envToBeApproved = "DEV";
			} else if(topicDetails.getSitStatus().equals("Pending")) {
				envToBeApproved = "SIT";
			} else if(topicDetails.getUatStatus().equals("Pending")) {
				envToBeApproved = "UAT";
			} else if(topicDetails.getProdStatus().equals("Pending")) {
				envToBeApproved = "PROD";
			}

			Statement stmt = conn.createStatement();
			boolean result = stmt.execute("Update Topics Set " + envToBeApproved +"_Status = 'Approved' Where Name='"+topicDetails.getTopicName()+"'");
			
		} catch (Exception e){
			System.out.println(e);
			resultMsg = "falied";
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return resultMsg;
	}
}
