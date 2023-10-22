package fr.findByDev.api.services.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.findByDev.api.models.Message;
import fr.findByDev.api.repositories.global.MessageRepository;

@Service
public class MessageService {
        private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
