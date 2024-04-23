package com.example.prompt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;
@EnableJms
@SpringBootApplication
public class PromptEmitterApp {
	@Autowired
	JmsTemplate jmsTemplate;

	@EventListener(ApplicationReadyEvent.class)
	public void doInitAfterStartup() {
		//enable to be in topic mode! to do at start
		jmsTemplate.setPubSubDomain(true);
	}


	public static void main(String[] args) {
		SpringApplication.run(PromptEmitterApp.class, args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
