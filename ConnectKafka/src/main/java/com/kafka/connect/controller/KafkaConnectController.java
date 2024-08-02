package com.kafka.connect.controller;

import java.util.List;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kafka.connect.model.KafkaConfig;
import com.kafka.connect.model.TopicDetails;
import com.kafka.connect.model.TopicDomain;
import com.kafka.connect.service.TopicService;

@Controller
public class KafkaConnectController {
	
//	@Autowired
//	private AdminClient adminClient;
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private KafkaConfig kafkaConfig;
	
	@GetMapping("/login")
	public String login() {
		
		return "index";
	}
	
	@PostMapping("/topics")
	public String get(@RequestParam("userName") String userName, Model model) {
		
		if(userName.equals("admin")) {
			return viewTopics(model);
		} else {
			TopicDomain topicDomain = new TopicDomain();
			model.addAttribute("topicDomain", topicDomain);
			model.addAttribute("userName",userName);
			return "create";
		}
	}

	public String viewTopics(Model model) {
		List<TopicDetails> topics = topicService.getAllTopics();
		model.addAttribute("topics", topics);
		return "viewTopics";
	}

	
	@PostMapping("/addTopic")
	public String addTopic(@ModelAttribute("topicDomain") TopicDomain topicDomain, @RequestParam("userName") String userName, Model model) throws Exception {
		
		String finalTopicName = topicDomain.getDomain() + "." +topicDomain.getSubdomain()+ "." +topicDomain.getEnvironment()+ "." +topicDomain.getTopicName();
		System.out.println("################# Topic: " + finalTopicName);
		model.addAttribute("result", topicService.addTopic(finalTopicName, userName));
		model.addAttribute("userName", userName);
		return "create";
	}
	
	@PostMapping("/doCreate")
	public String doCreateTopic(@ModelAttribute("topic") TopicDetails topicDetails, Model model) throws Exception {
		
		model.addAttribute("result", topicService.createTopic(topicDetails));
		return viewTopics(model);
		
	}

}
