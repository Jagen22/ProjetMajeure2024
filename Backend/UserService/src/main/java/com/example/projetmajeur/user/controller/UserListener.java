package com.example.projetmajeur.user.controller;

/*import javax.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import com.example.projetmajeur.user.model.UserDTO;

@Component
public class UserListener {
	
	private final UserService userService;
	public UserListener(UserService userService) {
		this.userService=userService;
	}
	
    @Autowired
    JmsTemplate jmsTemplate;

    @JmsListener(destination = "RESULT_BUS_MNG", containerFactory = "connectionFactory")
    public void receiveMessageResult(UserDTO user, Message message) {

            System.out.println("[BUSLISTENER] [CHANNEL RESULT_BUS_MNG] RECEIVED User MSG=["+user+"]");
            
    }

    @JmsListener(destination = "A", containerFactory = "connectionFactory")
    public void receiveMessageA(UserDTO user, Message message) {

    	userService.updateUser(user);

    }

    @JmsListener(destination = "B", containerFactory = "connectionFactory")
    public void receiveMessageB(UserDTO user, Message message) {

        System.out.println("[BUSLISTENER] [CHANNEL B] RECEIVED Poney MSG=["+user+"]");

    }
}*/