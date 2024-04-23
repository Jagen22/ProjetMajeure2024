package com.example.prompt.controller;

import com.example.prompt.model.PromptDTO;
import jakarta.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class PromptListener {
	
	private final PromptService promptService;
	public PromptListener(PromptService promptService) {
		this.promptService=promptService;
	}
	
    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = "A", containerFactory = "connectionFactory")
    public void receiveNewPrompt(PromptDTO promptDTO, Message message) {
        System.out.println(promptDTO.getId());
    	promptService.newPrompt(promptDTO);
    }

    @JmsListener(destination = "B", containerFactory = "connectionFactory")
    public void receiveNewProfile(PromptDTO promptDTO, Message message) {
        System.out.println(promptDTO.getId());
        promptService.newProfile(promptDTO);
    }
}