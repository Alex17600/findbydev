package fr.findByDev.api.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import fr.findByDev.api.models.Message;


public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send-message")
    @SendTo("/topic/messages")
    public Message sendMessage(@Payload Message message) {


        messagingTemplate.convertAndSend("/topic/messages", message);

       
        return message;
    }
}
